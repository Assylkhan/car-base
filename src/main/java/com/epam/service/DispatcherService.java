package com.epam.service;

import com.epam.dao.DaoFactory;
import com.epam.dao.DaoManager;
import com.epam.dao.DispatcherDao;
import com.epam.entity.Dispatcher;

public class DispatcherService {
    private DaoFactory factory;

    public DispatcherService(DaoFactory factory) {
        this.factory = factory;
    }

    public Dispatcher insert(Dispatcher dispatcher) {
        try (DaoManager daoManager = factory.getDaoManager()) {
            DispatcherDao dispatcherDao = daoManager.getDispatcherDao();
            Dispatcher insertedDispatcher = dispatcherDao.insert(dispatcher);
            return insertedDispatcher;
        }
    }

    public Dispatcher findByCredentials(String login, String password) {
        try (DaoManager daoManager = factory.getDaoManager()) {
            DispatcherDao dispatcherDao = daoManager.getDispatcherDao();
            Dispatcher dispatcher = dispatcherDao.findByCredentials(login, password);
            return dispatcher;
        }
    }

}
