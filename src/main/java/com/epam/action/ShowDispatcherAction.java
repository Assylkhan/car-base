package com.epam.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowDispatcherAction implements Action {
    ActionResult result = new ActionResult("dispatcherProfile");
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext servletContext = req.getServletContext();
        return result;
    }
}
