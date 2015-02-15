package com.epam.service;

import com.epam.dao.*;
import com.epam.entity.Client;
import com.epam.entity.Driver;
import com.epam.entity.Order;

public class ClientService {
    private DaoFactory factory;
    public ClientService(DaoFactory factory){
        this.factory = factory;
    }

    public Client findById(Long id){
        Client client = null;
        try(DaoManager daoManager = factory.getDaoManager()) {
            ClientDao clientDao = daoManager.getClientDao();
            client = clientDao.findById(id);
            OrderDao orderDao = daoManager.getOrderDao();
            Order order = orderDao.findNotCompletedByClientId(client.getId());
            if (order.getDriver()!=null){
                DriverDao driverDao = daoManager.getDriverDao();
                Driver driver = driverDao.findById(order.getDriver().getId());
                order.setDriver(driver);
            }
            client.setCurrentOrder(order);
        }
        return client;
    }
    public Client insert(Client client){
        try(DaoManager daoManager = factory.getDaoManager()) {
            ClientDao clientDao = daoManager.getClientDao();
            Client insertedClient = clientDao.insert(client);
            return insertedClient;
        }
    }

    public Client findByCredentials(String login, String password){
        try(DaoManager daoManager = factory.getDaoManager()) {
            ClientDao clientDao = daoManager.getClientDao();
            Client client = clientDao.findByCredentials(login, password);
            return client;
        }
    }
}