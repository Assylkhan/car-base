package com.epam.servlet;

import com.epam.action.Action;
import com.epam.action.ActionFactory;
import com.epam.action.ActionResult;
import com.epam.dao.DaoFactory;
import com.epam.entity.Announcement;
import com.epam.entity.Comment;
import com.epam.service.AnnouncementService;
import com.epam.service.CommentService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Controller extends HttpServlet {

    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        actionFactory = new ActionFactory();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String substring = req.getPathInfo();
        String actionName = req.getMethod() + substring;
        Action action = actionFactory.getAction(actionName);

        if (action == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
            return;
        }

        if (req.getSession().getAttribute("comments") == null) {
            DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
            CommentService commentService = new CommentService(daoFactory);
            List<Comment> comments = commentService.findRecentWithLimit(3);
            req.getSession().setAttribute("comments", comments);
        }
        if (req.getSession().getAttribute("news") == null) {
            DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
            AnnouncementService announcementService = new AnnouncementService(daoFactory);
            List<Announcement> announcements = announcementService.findRecentWithLimit(3);
            req.getSession().setAttribute("announcements", announcements);
        }

        ActionResult result = action.execute(req, resp);

        doForwardOrRedirect(result, req, resp);
    }

    private void doForwardOrRedirect(ActionResult result, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (result.isRedirect()) {
            String location = req.getContextPath() + "/" + result.getView();
            resp.sendRedirect(location);
        } else if (result.getView().startsWith("json")) {
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(new Gson().toJson(req.getAttribute("data")));
        } else {
            String path = String.format("/WEB-INF/jsp/" + result.getView() + ".jsp");
            req.getRequestDispatcher(path).forward(req, resp);
        }
    }
}
