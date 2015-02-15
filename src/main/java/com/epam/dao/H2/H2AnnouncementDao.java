package com.epam.dao.H2;

import com.epam.dao.DaoException;
import com.epam.dao.AnnouncementDao;
import com.epam.entity.Announcement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class H2AnnouncementDao implements AnnouncementDao {

    private Connection connection;

    public H2AnnouncementDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Object insert(Object newEntity) throws DaoException {
        return null;
    }

    @Override
    public void update(Object entity) throws DaoException {

    }

    @Override
    public void deleteById(Long id) throws DaoException {

    }

    @Override
    public Object findById(Long id) throws DaoException {
        return null;
    }

    @Override
    public List findAll() throws DaoException {
        return null;
    }

    @Override
    public List<Announcement> findLastWithLimit(int amount) throws DaoException {
        String SELECT_LAST_WITH_LIMIT = "SELECT * FROM ANNOUNCEMENT GROUP BY ID LIMIT ";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SELECT_LAST_WITH_LIMIT + amount)) {
                List<Announcement> announcement = new ArrayList<>();
                while (resultSet.next()) {
                    announcement.add(getAnnouncementBean(resultSet));
                }
                return announcement;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Announcement getAnnouncementBean(ResultSet resultSet) throws SQLException {
        Announcement announcement = new Announcement();
        announcement.setId(resultSet.getLong("ID"));
        announcement.setBody(resultSet.getString("BODY"));
        return announcement;
    }
}
