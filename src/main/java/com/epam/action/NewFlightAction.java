package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.dao.DaoManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewFlightAction implements Action {
    ActionResult flights = new ActionResult("flights");
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        // todo: finish the method
        ServletContext servletContext = req.getSession().getServletContext();
        DaoFactory daoFactory = (DaoFactory)servletContext.getAttribute("daoFactory");
        DaoManager daoManager = daoFactory.getDaoManager();
        return null;
    }
}
