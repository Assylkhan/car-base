package com.epam.dao.H2;

import com.epam.dao.DaoException;
import com.epam.dao.OrderDao;
import com.epam.entity.*;
import com.epam.entity.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2OrderDao implements OrderDao {
    private Connection connection;
    private static final String INSERT = "INSERT INTO ORDERS VALUES(NULL,?,?,?,?,DEFAULT,?,?,?)";
    private static final String UPDATE_DRIVER_COST_STATUS = "UPDATE ORDERS SET DRIVER_ID=COALESCE(?, DRIVER_ID), " +
            "COST = COALESCE(?,COST), STATUS_ID=COALESCE(?, STATUS_ID) WHERE ID=?";
    private static final String JOIN = " INNER JOIN STATUS ON STATUS.ID=ORDERS.STATUS_ID ";
    private static final String SELECT_BY_ID = "SELECT * FROM ORDERS" + JOIN + "WHERE ORDERS.ID = ?";
    private static final String SELECT_ALL = "SELECT * FROM ORDERS" + JOIN;
    private static final String SELECT_NOT_COMPLETED_BY_DRIVER_ID = "SELECT * FROM ORDERS" + JOIN +
            "WHERE DRIVER_ID=? AND STATUS_ID<>5";
    private static final String SELECT_NOT_COMPLETED_BY_CLIENT_ID = "SELECT * FROM ORDERS" + JOIN +
            "WHERE CLIENT_ID=? AND STATUS_ID<>5";
    private static final String SELECT_NOT_COMPLETED = "SELECT * FROM ORDERS" + JOIN + "WHERE STATUS_ID<>5";
    private static final String DELETE_DRIVER_ID_BY_ID = "UPDATE ORDERS SET DRIVER_ID=NULL WHERE ID=?";

    public H2OrderDao(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * @param order
     * @return generated id of inserted row in table
     * @throws DaoException
     */
    @Override
    public Order insert(Order order) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getClient().getId());
            statement.setString(2, order.getCarClass().toString());
            statement.setString(3, order.getPickupLocation());
            statement.setString(4, order.getDropOffLocation());
            if (order.getDriver() == null)
                statement.setNull(5, Types.BIGINT);
            else
                statement.setLong(5, order.getDriver().getId());
            if (order.getCost() == null)
                statement.setNull(6, Types.DECIMAL);
            else
                statement.setBigDecimal(6, order.getCost());
            statement.setInt(7, getIdFromEnum(order.getStatus()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("inserting flight failed, no rows affected");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    order.setId(id);
                } else {
                    throw new SQLException("inserting flight failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return order;
    }

    /**
     * updates row in table with given fields of Order bean
     * @param order
     * @throws DaoException
     */
    @Override
    public void update(Order order) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DRIVER_COST_STATUS)) {
            if (order.getDriver() == null)
                statement.setNull(1, Types.BIGINT);
            else
                statement.setLong(1, order.getDriver().getId());
            statement.setBigDecimal(2, order.getCost());
            if (order.getStatus() == null)
                statement.setNull(3, Types.INTEGER);
            else
                statement.setInt(3, getIdFromEnum(order.getStatus()));
            statement.setLong(4, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param id
     * @return orders collection where column 'status' not equals to 'completed' by id of given Driver
     * @throws DaoException
     */
    @Override
    public Order findNotCompletedByDriverId(Long id) throws DaoException {
        Order order = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_NOT_COMPLETED_BY_DRIVER_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) order = getOrderBean(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return order;
    }

    /**
     *
     * @param id
     * @return orders collection where column 'status' not equals to 'completed' by id of given Client
     * @throws DaoException
     */
    @Override
    public Order findNotCompletedByClientId(Long id) throws DaoException {
        Order order = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_NOT_COMPLETED_BY_CLIENT_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) order = getOrderBean(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return order;
    }

    /**
     *
     * @return orders collection where column 'status' not equals to 'completed'
     * @throws DaoException
     */
    @Override
    public List<Order> findNotCompleted() throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_NOT_COMPLETED)) {
            while (resultSet.next()) {
                orders.add(getOrderBean(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }

    /**
     * remove entry from table 'driver' by given id
     * @param driver
     * @throws DaoException
     */
    @Override
    public void removeDriver(Driver driver) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_DRIVER_ID_BY_ID)) {
            statement.setLong(1, driver.getCurrentOrder().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws DaoException {
    }

    @Override
    public Order findById(Long id) throws DaoException {
        Order order = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) order = getOrderBean(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return order;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
            while (resultSet.next()) {
                orders.add(getOrderBean(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }

    /**
     *
     * @param resultSet
     * @return created bean with initialized fields accordingly with ResultSet
     * @throws java.sql.SQLException
     */
    public Order getOrderBean(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("ID"));
        /*long driver_id = resultSet.getLong("DRIVER_ID");
        if (driver_id != 0) {
            Driver driver = new Driver();
            driver.setId(resultSet.getLong("DRIVER_ID"));
            order.setDriver(driver);
        }
        Client client = new Client();
        client.setId(resultSet.getLong("CLIENT_ID"));
        order.setClient(client);*/
        order.setCarClass(CarClass.valueOf(resultSet.getString("CAR_CLASS")));
        order.setPickupLocation(resultSet.getString("PICKUP_LOCATION"));
        order.setDropOffLocation(resultSet.getString("DROP_OFF_LOCATION"));
        order.setReceivedTime(resultSet.getTimestamp("RECEIVED_TIME"));
        order.setStatus(resultSet.getString("VALUE"));
        order.setCost(resultSet.getBigDecimal("COST"));
        return order;
    }

    /**
     *
     * @param status
     * @return let say 'index' of apparently enum instance. If param value is null returns null.
     */
    private Integer getIdFromEnum(Order.OrderStatus status) {
        switch (status) {
            case NOT_SERVED:
                return 1;
            case ACCEPTED:
                return 2;
            case IN_PROCESS:
                return 3;
            case CLIENT_EXPECTING:
                return 4;
            case COMPLETED:
                return 5;
            default:
                return null;
        }
    }
}
