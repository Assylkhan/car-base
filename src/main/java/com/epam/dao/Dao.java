package com.epam.dao;

import java.util.List;

public interface Dao<T> {
    public T insert(T newEntity) throws DaoException;
    public void update(T entity) throws DaoException;
    public void deleteById(Long id) throws DaoException;
    public T findById(Long id) throws DaoException;
    public List<T> findAll() throws DaoException;
}
