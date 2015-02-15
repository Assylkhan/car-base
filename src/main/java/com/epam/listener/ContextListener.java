package com.epam.listener;

import com.epam.dao.DaoFactory;
import com.epam.dao.DatabaseType;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    DaoFactory factory;

    /**
     *  put DaoFactory in servlet context which will use for entire application
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        factory = DaoFactory.getDaoFactory(DatabaseType.H2);
        servletContext.setAttribute("daoFactory", factory);
        System.out.println("context initialized");
    }

    /**
     * will be released all connections of connection pool which is common for entire application
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (factory != null) factory.releaseConnections();
        System.out.println("context destroyed");
    }
}
