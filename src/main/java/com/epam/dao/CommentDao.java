package com.epam.dao;

import com.epam.entity.Comment;

import java.util.List;

public interface CommentDao extends Dao<Comment> {
   public Comment findByClientId(Long id) throws DaoException;
   public List<Comment> findLastWithLimit(int amount) throws DaoException;
}
