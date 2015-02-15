package com.epam.dao.H2;

import com.epam.dao.DaoException;
import com.epam.dao.DispatcherDao;
import com.epam.entity.Dispatcher;
import com.epam.entity.Role;

import java.sql.*;
import java.util.List;

public class H2DispatcherDao implements DispatcherDao {
    private Connection connection = null;
    private static final String SELECT_BY_ID = "SELECT * FROM DISPATCHER WHERE DISPATCHER.ID = ?";
    private static final String SELECT_BY_CREDENTIALS = "SELECT * FROM DISPATCHER WHERE DISPATCHER.LOGIN = ? AND DISPATCHER.PASSWORD = ?";
    private static final String INSERT = "INSERT INTO DISPATCHER (LOGIN, PASSWORD, FIRST_NAME, LAST_NAME, NATIONAL_ID, PHONE) VALUES (?, ?, ?, ?, ?, ?)";

    public H2DispatcherDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Dispatcher insert(Dispatcher dispatcher) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dispatcher.getLogin());
            statement.setString(2, dispatcher.getPassword());
            statement.setString(3, dispatcher.getFirstName());
            statement.setString(4, dispatcher.getLastName());
            statement.setString(5, dispatcher.getNationalId());
            statement.setString(6, dispatcher.getPhone());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    dispatcher.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return dispatcher;
    }

    @Override
    public void update(Dispatcher entity) throws DaoException {
    }

    @Override
    public void deleteById(Long id) throws DaoException {
    }

    public Dispatcher findByCredentials(String login, String password) throws DaoException {
        Dispatcher dispatcher = null;
        try (PreparedStatement statement = connection.prepareCall(SELECT_BY_CREDENTIALS)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) dispatcher = getDispatcherBean(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return dispatcher;
    }

    @Override
    public Dispatcher findById(Long id) throws DaoException {
        Dispatcher dispatcher = null;
        try (PreparedStatement statement = connection.prepareCall(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) dispatcher = getDispatcherBean(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return dispatcher;
    }

    @Override
    public List<Dispatcher> findAll() throws DaoException {
        return null;
    }

    /**
     * @param resultSet
     * @return Dispatcher bean with initialized fields
     * @throws java.sql.SQLException
     */
    private Dispatcher getDispatcherBean(ResultSet resultSet) throws SQLException {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setId(resultSet.getLong("ID"));
        dispatcher.setLogin(resultSet.getString("login"));
        dispatcher.setPassword(resultSet.getString("password"));
        dispatcher.setFirstName(resultSet.getString("first_name"));
        dispatcher.setLastName(resultSet.getString("last_name"));
        dispatcher.setNationalId(resultSet.getString("national_id"));
        dispatcher.setPhone(resultSet.getString("phone"));
        dispatcher.setRole(Role.DISPATCHER);
        return dispatcher;
    }
}
