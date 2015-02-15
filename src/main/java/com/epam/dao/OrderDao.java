package com.epam.dao;

import com.epam.entity.Driver;
import com.epam.entity.Order;

import java.util.List;

public interface OrderDao extends Dao<Order> {
    /**
     *
     * @param id
     * @return Order by Driver id where order not completed yet
     * @throws DaoException
     */
    public Order findNotCompletedByDriverId(Long id) throws DaoException;

    /**
     *
     * @param id
     * @return not completed order by given client id
     * @throws DaoException
     */
    public Order findNotCompletedByClientId(Long id) throws DaoException;

    /**
     *
     * @return not completed orders collection
     * @throws DaoException
     */
    public List<Order> findNotCompleted() throws DaoException;

    /**
     * removes entry of given driver
     * @param driver
     * @throws DaoException
     */
    public void removeDriver(Driver driver) throws DaoException;

}
