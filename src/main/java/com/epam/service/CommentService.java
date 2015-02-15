package com.epam.service;

import com.epam.dao.*;
import com.epam.entity.Comment;

import java.util.List;

public class CommentService {
    private DaoFactory daoFactory;

    public CommentService(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    public List<Comment> findRecentWithLimit(int amount){
        try(DaoManager daoManager = daoFactory.getDaoManager()){
            CommentDao commentDao = daoManager.getCommentDao();
            List<Comment> comments = commentDao.findLastWithLimit(amount);
            if (comments.size()>0){
                ClientDao clientDao = daoManager.getClientDao();
                comments.forEach(comment-> {
                    comment.setClient(clientDao.findByCommentId(comment.getId()));
                });
            }
            return comments;
        }
    }

    public List<Comment> findAll(){
        try(DaoManager daoManager = daoFactory.getDaoManager()){
            CommentDao commentDao = daoManager.getCommentDao();
            List<Comment> comments = commentDao.findAll();
            if (comments.size()>0){
                ClientDao clientDao = daoManager.getClientDao();
                comments.forEach(comment-> {
                    comment.setClient(clientDao.findByCommentId(comment.getId()));
                });
            }
            return comments;
        }
    }

    public Comment insert(Comment comment){
        try (DaoManager daoManager = daoFactory.getDaoManager()) {
            CommentDao commentDao = daoManager.getCommentDao();
            Comment insertedComment = commentDao.insert(comment);
            return insertedComment;
        }
    }
}
