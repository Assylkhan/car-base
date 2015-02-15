package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.entity.Comment;
import com.epam.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCommentsAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        DaoFactory daoFactory = (DaoFactory)req.getServletContext().getAttribute("daoFactory");
        CommentService commentService = new CommentService(daoFactory);
        List<Comment> comments = commentService.findAll();
        req.setAttribute("allComments", comments);
        return new ActionResult("comments");
    }
}
