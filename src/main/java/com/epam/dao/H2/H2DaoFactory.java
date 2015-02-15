package com.epam.dao.H2;

import com.epam.dao.DaoException;
import com.epam.dao.DaoFactory;
import com.epam.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class H2DaoFactory extends DaoFactory {
    private static final ResourceBundle resource = ResourceBundle.getBundle("database");
    private ConnectionPool pool = null;

    /**
     * constructor in which is initialized ConnectionPool with 10 connections size
     */
    public H2DaoFactory(){
        try {
            pool = new ConnectionPool(10);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void releaseConnections() {
        pool.closeConnections();
    }

    /**
     *
     * @return H2DaoManager with initialized Connection
     */
    public H2DaoManager getDaoManager(){
        Connection connection = null;
        try {
            connection = pool.getConnection();
        } catch (SQLException e) { }
        return new H2DaoManager(connection);
    }

    public void setConnectionPool(ConnectionPool pool){
        this.pool = pool;
    }
}
