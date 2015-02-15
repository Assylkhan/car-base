package com.epam.service;

import com.epam.dao.*;
import com.epam.entity.Client;
import com.epam.entity.Driver;
import com.epam.entity.Order;

import java.util.List;

public class OrderService {
    private DaoFactory daoFactory;

    public OrderService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Order insert(Order order) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            OrderDao orderDao = daoManager.getOrderDao();
            Order insertedOrder = orderDao.insert(order);
            return insertedOrder;
        }
    }

    public List<Order> findAll() {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            OrderDao orderDao = daoManager.getOrderDao();
            List<Order> orders = orderDao.findAll();
            if (orders.size() > 0) {
                DriverDao driverDao = daoManager.getDriverDao();
                ClientDao clientDao = daoManager.getClientDao();
                orders.forEach(order -> {
                    Driver driver = driverDao.findByOrderId(order.getId());
                    order.setDriver(driver);
                    Client client = clientDao.findByOrderId(order.getId());
                    order.setClient(client);
                });
            }
            return orders;
        }
    }

    public List<Order> findNotCompleted() {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            OrderDao orderDao = daoManager.getOrderDao();
            List<Order> orders = orderDao.findNotCompleted();
            if (orders != null) {
                orders.forEach(order -> {
                    ClientDao clientDao = daoManager.getClientDao();
                    Client client = clientDao.findByOrderId(order.getId());
                    order.setClient(client);
                });
            }
            return orders;
        }
    }

    public void update(Order order) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            OrderDao orderDao = daoManager.getOrderDao();
            orderDao.update(order);
        }
    }


    public void entrustToDriver(Order order) {
        DaoManager daoManager = daoFactory.getDaoManager();
        try {
            daoManager.beginTransaction();
            OrderDao orderDao = daoManager.getOrderDao();
            orderDao.update(order);
            DriverDao driverDao = daoManager.getDriverDao();
            driverDao.updateState(order.getDriver());
            daoManager.commit();
        } catch (DaoException e) {
            daoManager.rollback();
            throw e;
        } finally {
            daoManager.closeQuietly();
        }
    }

    public Order findNotCompletedByDriverId(Long id) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            OrderDao orderDao = daoManager.getOrderDao();
            Order order = orderDao.findNotCompletedByDriverId(id);
            if (order != null) {
                DriverDao driverDao = daoManager.getDriverDao();
                Driver driver = driverDao.findByOrderId(order.getId());
                order.setDriver(driver);
                ClientDao clientDao = daoManager.getClientDao();
                Client client = clientDao.findByOrderId(order.getId());
                order.setClient(client);
            }
            return order;
        }
    }

    public Order findNotCompletedByClientId(Long id) {
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            OrderDao orderDao = daoManager.getOrderDao();
            Order order = orderDao.findNotCompletedByClientId(id);
            if (order == null) return null;
            DriverDao driverDao = daoManager.getDriverDao();
            Driver driver = driverDao.findByOrderId(order.getId());
            order.setDriver(driver);
            ClientDao clientDao = daoManager.getClientDao();
            Client client = clientDao.findByOrderId(order.getId());
            order.setClient(client);
            return order;
        }
    }
}
