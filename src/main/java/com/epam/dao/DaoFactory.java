package com.epam.dao;

import com.epam.dao.H2.H2DaoFactory;

public abstract class DaoFactory {

    /**
     *
     * @param dbType
     * @return implementation of the interface according to passed database type
     */
    public static DaoFactory getDaoFactory(DatabaseType dbType){
        switch (dbType){
            case  H2: return new H2DaoFactory();
            default: return null;
        }
    }

    /**
     * release all connections in connection pool
     */
    public abstract void releaseConnections();

    /**
     *
     * @return concrete implementation of DaoManager
     */
    public abstract DaoManager getDaoManager();
}
