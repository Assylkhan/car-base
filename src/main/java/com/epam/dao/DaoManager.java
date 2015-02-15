package com.epam.dao;

public interface DaoManager extends AutoCloseable {

    public void beginTransaction() throws DaoException;
    public void commit() throws DaoException;
    public void rollback() throws DaoException;

    /**
     *
     * @throws DaoException
     */
    public void close() throws DaoException;

    /**
     * closes connection suppressing or swallowing thrown exception
     */
    public void closeQuietly();
    public ClientDao getClientDao() throws DaoException;
    public OrderDao getOrderDao() throws DaoException;
    public DriverDao getDriverDao() throws DaoException;
    public DispatcherDao getDispatcherDao() throws DaoException;
    public CommentDao getCommentDao() throws DaoException;

    AnnouncementDao getAnnouncementDao();
}
