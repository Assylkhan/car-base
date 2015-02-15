package com.epam.dao.H2;

import com.epam.dao.DaoException;
import com.epam.dao.DriverDao;
import com.epam.entity.Driver;
import com.epam.entity.Role;

import java.sql.*;
import java.util.*;

public class H2DriverDao implements DriverDao {
    private Connection connection;
    private static final String SELECT_BY_ID = "SELECT * FROM DRIVER WHERE ID = ?";
    private static final String INSERT = "INSERT INTO DRIVER (LOGIN, PASSWORD, FIRST_NAME, LAST_NAME, NATIONAL_ID, PHONE) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_CREDENTIALS = "SELECT * FROM DRIVER WHERE DRIVER.LOGIN = ? AND DRIVER.PASSWORD = ?";
    private static final String UPDATE = "UPDATE DRIVER SET LOGIN=?, PASSWORD=?, FIRST_NAME=?" +
            ",LAST_NAME=?,NATIONAL_ID=?,PHONE=?,AVAILABLE=?,CURRENT_LOCATION=?, GOV_NUMBER=?," +
            " CAR_MODEL=?, CAR_CLASS=?, CAR_STATE=?, CAR_IMAGE=? WHERE ID=?";
    private static final String UPDATE_STATE = "UPDATE DRIVER SET CURRENT_LOCATION=?, AVAILABLE=? WHERE ID=?";
    private static final String SELECT_WHERE_AVAILABLE = "SELECT * FROM DRIVER WHERE AVAILABLE=TRUE";
    private static final String JOIN = " INNER JOIN DRIVER ON DRIVER.ID=ORDERS.DRIVER_ID ";
    private static final String SELECT_BY_ORDER_ID = "SELECT * FROM ORDERS" + JOIN +
            "WHERE ORDERS.ID=? AND ORDERS.STATUS_ID<>5";


    public H2DriverDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Driver insert(Driver driver) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, driver.getLogin());
            statement.setString(2, driver.getPassword());
            statement.setString(3, driver.getFirstName());
            statement.setString(4, driver.getLastName());
            statement.setString(5, driver.getNationalId());
            statement.setString(6, driver.getPhone());
            statement.executeUpdate();
            if (statement.executeUpdate() == 0)
                throw new DaoException("inserting driver failed, no rows affected");
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    driver.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return driver;
    }

    @Override
    public void update(Driver driver) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, driver.getLogin());
            statement.setString(2, driver.getPassword());
            statement.setString(3, driver.getFirstName());
            statement.setString(4, driver.getLastName());
            statement.setString(5, driver.getNationalId());
            statement.setString(6, driver.getPhone());
            statement.setBoolean(7, driver.isAvailable());
            statement.setString(8, driver.getCurrentLocation());
            statement.setString(9, driver.getGovNumber());
            statement.setString(10, driver.getCarModel());
            statement.setString(11, driver.getCarClass().toString());
            statement.setString(12, driver.getCarState());
            statement.setString(13, driver.getCarImage());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    /**
     * updates driver's currentLocation, isAvailable fields
     *
     * @param driver
     * @throws DaoException
     */
    @Override
    public void updateState(Driver driver) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_STATE)) {
            statement.setString(1, driver.getCurrentLocation());
            statement.setBoolean(2, driver.isAvailable());
            statement.setLong(3, driver.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Driver findByOrderId(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ORDER_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Driver driver = getDriverBean(resultSet);
                return driver;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public void deleteById(Long id) throws DaoException {
    }

    @Override
    public Driver findById(Long id) throws DaoException {
        Driver driver = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) driver = getDriverBean(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return driver;
    }

    @Override
    public List<Driver> findAll() throws DaoException {
        List<Driver> drivers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM DRIVER")) {
                while (resultSet.next()) {
                    drivers.add(getDriverBean(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return drivers;
    }

    @Override
    public Driver findByCredentials(String login, String password) throws DaoException {
        Driver dispatcher = null;
        try (PreparedStatement statement = connection.prepareCall(SELECT_BY_CREDENTIALS)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) dispatcher = getDriverBean(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return dispatcher;
    }

    /**
     * @return list of drivers with field 'available' which value is true
     * @throws DaoException
     */
    @Override
    public List<Driver> findAvailableDrivers() throws DaoException {
        List<Driver> drivers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SELECT_WHERE_AVAILABLE)) {
                while (resultSet.next()) {
                    drivers.add(getDriverBean(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return drivers;
    }

    /**
     * @param resultSet
     * @return Driver bean with initialized fields accordingly ResultSet object
     * @throws java.sql.SQLException
     */
    private Driver getDriverBean(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver();
        driver.setId(resultSet.getLong("DRIVER.ID"));
        driver.setPhone(resultSet.getString("PHONE"));
        driver.setNationalId(resultSet.getString("NATIONAL_ID"));
        driver.setFirstName(resultSet.getString("FIRST_NAME"));
        driver.setLastName(resultSet.getString("LAST_NAME"));
        driver.setLogin(resultSet.getString("LOGIN"));
        driver.setPassword(resultSet.getString("PASSWORD"));
        driver.setAvailable(resultSet.getBoolean("AVAILABLE"));
        driver.setCurrentLocation(resultSet.getString("CURRENT_LOCATION"));
        driver.setCarClass(resultSet.getString("CAR_CLASS"));
        driver.setCarModel(resultSet.getString("CAR_MODEL"));
        driver.setCarState(resultSet.getString("CAR_STATE"));
        driver.setCarImage(resultSet.getString("CAR_IMAGE"));
        driver.setGovNumber(resultSet.getString("GOV_NUMBER"));
        driver.setRole(Role.DRIVER);
        return driver;
    }
}
