package com.epam.pool;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

public class ConnectionPool {
    private int connectionsInUse;
    private int maxConnectionCount;
    private int minConnectionCount = 5;
    private Connector connector = new Connector();
    private BlockingQueue<PooledConnection> freeConnections;
    private final Logger log = Logger.getLogger(ConnectionPool.class);
    private long maxIdleTime = 30 * 1000; // 5 seconds
    private int checkInterval = 30 * 1000;
    private Lock lock = new ReentrantLock();
    private PoolCleaner cleaner;

    public int getConnectionsInUse() {
        return connectionsInUse;
    }

    public int getMinConnectionCount() {
        return minConnectionCount;
    }

    public void setMinConnectionCount(int minConnectionCount) {
        this.minConnectionCount = minConnectionCount;
    }

    public int getMaxConnectionCount() {
        return maxConnectionCount;
    }

    public BlockingQueue<PooledConnection> getFreeConnections() {
        return freeConnections;
    }

    public ConnectionPool(int maxConnectionCount) throws SQLException {
        this.maxConnectionCount = maxConnectionCount;
        this.connector = new Connector();
        init();
    }

    public ConnectionPool() throws SQLException {
        maxConnectionCount = 100;
        this.connector = new Connector();
        init();
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public Connector getConnector() {
        return connector;
    }

    private void init() throws SQLException {
        freeConnections = new ArrayBlockingQueue<PooledConnection>(maxConnectionCount);
        for (int i = 0; i < minConnectionCount; i++) {
            PooledConnection pooledConnection = getNewConnection();
            try {
                freeConnections.put(pooledConnection);
                pooledConnection.addedToQueueTime = System.currentTimeMillis();
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
        cleaner = new PoolCleaner(checkInterval);
        cleaner.start();
    }

    public void setMaxConnectionCount(int maxConnectionCount) {
        this.maxConnectionCount = maxConnectionCount;
    }

    public Connection getConnection() throws SQLException {
        synchronized (freeConnections) {
            PooledConnection connection = freeConnections.poll();
            if (connection == null) {
                if (connectionsInUse < maxConnectionCount) {
                    freeConnections.offer(getNewConnection());
                    connection = freeConnections.poll();
                } else {
                    try {
                        connection = freeConnections.take();
                    } catch (InterruptedException e) {
                        log.error(e);
                    }
                }
            }
            connectionsInUse++;
            if (cleaner == null) {
                cleaner = new PoolCleaner(checkInterval);
                cleaner.start();
            }
            return connection;
        }
    }

    private PooledConnection getNewConnection() throws SQLException {
        PooledConnection pooledConnection = new PooledConnection(connector.getConnection());
        return pooledConnection;
    }

    public void closeConnections() {
        for (PooledConnection pooledConnection : freeConnections) {
            try {
                pooledConnection.connection.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        freeConnections.clear();
        if (cleaner != null) cleaner.terminateCleaner();
    }

    private class PooledConnection implements Connection {
        private Connection connection;
        private long addedToQueueTime;

        public long getAddedToQueueTime() {
            return addedToQueueTime;
        }

        public PooledConnection(Connection connection) throws SQLException {
            this.connection = connection;
            this.connection.setAutoCommit(true);
        }

        @Override
        public synchronized void close() throws SQLException {
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            try {
                freeConnections.put(this);
                connectionsInUse--;
            } catch (InterruptedException e) {
                log.error(e);
            }
            this.addedToQueueTime = System.currentTimeMillis();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }
    }

    public void closeExpired() {
        synchronized (freeConnections) {
            Iterator<PooledConnection> connections = freeConnections.iterator();
            while (connections.hasNext()) {
                PooledConnection pooledConnection = connections.next();
                long idleTime = System.currentTimeMillis() - pooledConnection.addedToQueueTime;
                if (idleTime >= maxIdleTime) {
                    try {
                        pooledConnection.connection.close();
                    } catch (SQLException e) { }
                    boolean remove = freeConnections.remove(pooledConnection);
//                pooledConnection = null;
                }
            }
            if (freeConnections.size() == 0 && cleaner != null) {
                cleaner.terminateCleaner();
                cleaner = null;
            }
        }
    }

    private class PoolCleaner extends Thread {
        private int checkInterval;
        private boolean mustStop;

        public PoolCleaner(int checkInterval) {
            if (checkInterval < 0) throw new IllegalArgumentException("checking interval must be larger than 0");
            this.checkInterval = checkInterval;
            setDaemon(true);
        }

        @Override
        public void run() {
            while (!mustStop) {
                try {
                    sleep(checkInterval);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
                closeExpired();
            }
        }

        public void terminateCleaner() {
            mustStop = true;
        }
    }
}