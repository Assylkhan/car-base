package com.epam.dao;

import com.epam.entity.Client;

public interface ClientDao extends Dao<Client> {
    /**
     *
     * @param login
     * @return Client object with initialized fields
     * @throws DaoException
     */
    public Client findByLogin(String login) throws DaoException;

    /**
     *
     * @param login
     * @param password
     * @return Client object with initialized fields
     * @throws DaoException
     */
    public Client findByCredentials(String login, String password) throws DaoException;

    public Client findByOrderId(Long id) throws DaoException;

    public Client findByCommentId(Long id) throws DaoException;
}
