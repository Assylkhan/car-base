package com.epam.dao.H2;

import com.epam.dao.CommentDao;
import com.epam.dao.DaoException;
import com.epam.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2CommentDao implements CommentDao {
    private static final String INSERT = "INSERT INTO DRIVER VALUES (NULL, ?, ?, NULL)";
    private static final String SELECT_ALL = "SELECT * FROM COMMENT";
    private static final String SELECT_BY_CLIENT_ID = "SELECT * FROM COMMENT WHERE CLIENT_ID=?";
    private static final String SELECT_BY_ID = "SELECT * FROM COMMENT WHERE ID=?";
    private Connection connection;

    public H2CommentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Comment insert(Comment comment) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setLong(1, comment.getClient().getId());
            statement.setString(2, comment.getBody());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) throw new DaoException("inserting comment failed, no rows affected");
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) comment.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return comment;
    }

    @Override
    public void update(Comment entity) throws DaoException {

    }

    @Override
    public void deleteById(Long id) throws DaoException {

    }

    @Override
    public Comment findById(Long id) throws DaoException {
        return null;
    }

    @Override
    public List<Comment> findAll() throws DaoException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
                List<Comment> comments = new ArrayList<>();
                while (resultSet.next()) {
                    comments.add(getCommentBean(resultSet));
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Comment findByClientId(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_CLIENT_ID)) {
            statement.setLong(1, id);
//            try(ResultSet resultSet = statement.executeQuery()){
//
//            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return null;
    }

    @Override
    public List<Comment> findLastWithLimit(int amount) throws DaoException {
        String SELECT_LAST_WITH_LIMIT = "SELECT * FROM COMMENT GROUP BY ID LIMIT ";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SELECT_LAST_WITH_LIMIT + amount)) {
                List<Comment> comments = new ArrayList<>();
                while (resultSet.next()) {
                    comments.add(getCommentBean(resultSet));
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Comment getCommentBean(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong("ID"));
        comment.setBody(resultSet.getString("COMMENT_BODY"));
        comment.setLeaveDate(resultSet.getTimestamp("LEAVE_DATE"));
        return comment;
    }
}
