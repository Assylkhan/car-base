package com.epam.dao.H2;

import com.epam.dao.ClientDao;
import com.epam.dao.DaoException;
import com.epam.entity.Client;
import com.epam.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2ClientDao implements ClientDao {
    private static final String INSERT_CLIENT = "INSERT INTO CLIENT (LOGIN, PASSWORD, FIRST_NAME, LAST_NAME) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_LOGIN = "SELECT * FROM CLIENT WHERE LOGIN = ?";
    private static final String SELECT_BY_LOGIN_AND_PASSWORD = "SELECT * FROM CLIENT WHERE LOGIN = ? AND PASSWORD = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM CLIENT WHERE ID = ?";
    private static final String DELETE_BY_ID = "DELETE FROM CLIENT WHERE ID = ?";
    private static final String ORDER_JOIN = " INNER JOIN CLIENT ON CLIENT.ID=ORDERS.CLIENT_ID ";
    private static final String COMMENT_JOIN = " INNER JOIN CLIENT ON CLIENT.ID=COMMENT.CLIENT_ID ";
    private static final String SELECT_BY_ORDER_ID = "SELECT * FROM ORDERS" + ORDER_JOIN +
            "WHERE ORDERS.ID=? AND ORDERS.STATUS_ID<>5";
    private static final String SELECT_BY_COMMENT_ID = "SELECT * FROM COMMENT" + COMMENT_JOIN +
            "WHERE COMMENT.ID=?";
    private Connection connection = null;

    public H2ClientDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * @param client
     * @return generated id of inserted row
     * @throws DaoException
     */
    @Override
    public Client insert(Client client) throws DaoException {
        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_CLIENT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, client.getLogin());
            statement.setString(2, client.getPassword());
            statement.setString(3, client.getFirstName());
            statement.setString(4, client.getLastName());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    client.setId(id);
                } else
                    throw new DaoException("clientDao.insert(): not executed");

            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return client;
    }

    @Override
    public void update(Client entity) throws DaoException {
    }

    /**
     * @param id
     * @throws DaoException
     */
    @Override
    public void deleteById(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            boolean executed = statement.execute();
            if (!executed) throw new DaoException("clientDao.deleteById(): not executed");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * @param id
     * @return Client
     * @throws DaoException
     */
    @Override
    public Client findById(Long id) throws DaoException {
        Client client = null;
        try (PreparedStatement statement = connection.prepareCall(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    client = getClientBean(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return client;
    }

    /**
     * @param login
     * @return Client
     * @throws DaoException
     */
    @Override
    public Client findByLogin(String login) throws DaoException {
        Client client = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    client = getClientBean(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return client;
    }

    /**
     * @param login
     * @param password
     * @return Client
     * @throws DaoException
     */
    @Override
    public Client findByCredentials(String login, String password) throws DaoException {
        Client client = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) client = getClientBean(resultSet);
            }
        } catch (Exception e) {
            throw new DaoException();
        }
        return client;
    }

    @Override
    public Client findByOrderId(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ORDER_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Client client = getClientBean(resultSet);
                return client;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Client findByCommentId(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_COMMENT_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Client client = null;
                if (resultSet.next()) client = getClientBean(resultSet);
                return client;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * @return
     * @throws DaoException
     */
    @Override
    public List<Client> findAll() throws DaoException {
        List<Client> clients = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM CLIENT")) {
            while (resultSet.next()) {
                clients.add(getClientBean(resultSet));
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return clients;
    }

    /**
     * creates Client bean from ResultSet
     *
     * @param resultSet
     * @return
     * @throws java.sql.SQLException
     */
    public Client getClientBean(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getLong("CLIENT.ID"));
        client.setFirstName(resultSet.getString("FIRST_NAME"));
        client.setLastName(resultSet.getString("LAST_NAME"));
        client.setLogin(resultSet.getString("LOGIN"));
        client.setPassword(resultSet.getString("PASSWORD"));
        client.setBill(resultSet.getBigDecimal("BILL"));
        client.setRole(Role.CLIENT);
        return client;
    }
}
