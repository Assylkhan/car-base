package com.epam.dao;

import com.epam.entity.Driver;

import java.util.List;

public interface DriverDao extends Dao<Driver> {
    /**
     *
     * @param login
     * @param password
     * @return Driver with initialized fields
     * @throws DaoException
     */
    public Driver findByCredentials(String login, String password) throws DaoException;

    /**
     *
     * @return available drivers collection with initialized fields
     * @throws DaoException
     */
    public List<Driver> findAvailableDrivers() throws DaoException;

    /**
     * update fields of driver which describes its state
     * @param driver
     * @throws DaoException
     */
    public void updateState(Driver driver) throws DaoException;

    public Driver findByOrderId(Long id) throws DaoException;
}
