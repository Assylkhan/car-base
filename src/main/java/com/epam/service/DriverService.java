package com.epam.service;

import com.epam.dao.*;
import com.epam.entity.Driver;
import com.epam.entity.Order;

import java.util.List;

/**
 * in class executed business logic of app
 */
public class DriverService {

    private DaoFactory daoFactory;

    /**
     * @param factory
     */
    public DriverService(DaoFactory factory) {
        this.daoFactory = factory;
    }

    /**
     * @param dispatcher
     * @return updated driver with generated id
     */
    public Driver insert(Driver dispatcher) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            DriverDao driverDao = daoManager.getDriverDao();
            Driver insertedDriver = driverDao.insert(dispatcher);
            return insertedDriver;
        }
    }

    /**
     * @param id
     * @return Driver
     */
    public Driver findById(Long id) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            DriverDao driverDao = daoManager.getDriverDao();
            Driver driver = driverDao.findById(id);
            OrderDao orderDao = daoManager.getOrderDao();
            // finding last order of given driver
            Order order = orderDao.findNotCompletedByDriverId(id);
            driver.setCurrentOrder(order);
            return driver;
        }
    }

    /**
     * updates driver's currentLocation, isAvailable fields
     *
     * @param driver
     */
    public void updateState(Driver driver) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            DriverDao driverDao = daoManager.getDriverDao();
            driverDao.updateState(driver);
        }
    }

    /**
     * @param driver updates orders table by putting or removing driver_id
     */
    public void updateOrderByDriver(Driver driver) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            OrderDao orderDao = daoManager.getOrderDao();
            Order currentOrder = driver.getCurrentOrder();
            currentOrder.setDriver(driver);
            orderDao.update(currentOrder);
        }
    }

    /**
     * @return available drivers whose field available is true
     */
    public List<Driver> findAvailableDrivers() {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            DriverDao driverDao = daoManager.getDriverDao();
            List<Driver> drivers = driverDao.findAvailableDrivers();
            return drivers;
        }
    }
}