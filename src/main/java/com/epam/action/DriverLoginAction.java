package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.entity.*;
import com.epam.service.UserService;
import com.epam.util.HashGenerator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DriverLoginAction implements Action {
    ActionResult result = new ActionResult("driverProfile", true);
    ActionResult loginAgain = new ActionResult("driverLogin");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String generatedPassword = HashGenerator.passwordToHash(password);
        ServletContext servletContext = req.getSession().getServletContext();
        DaoFactory daoFactory = (DaoFactory) servletContext.getAttribute("daoFactory");
        UserService userService = new UserService(daoFactory);
        Driver driver = (Driver) userService.findByCredentials(login, generatedPassword, Role.DRIVER);
        if (driver == null) {
            req.setAttribute("loginError", "login.loginOrPasswordIncorrect");
            return loginAgain;
        }
        HttpSession session = req.getSession();
        session.setAttribute("driver", driver);
        return result;
        /*Cookie cookie = new Cookie("sessionId", session.getId());
        cookie.setMaxAge(24 * 60 * 60);
        resp.addCookie(cookie);*/
    }
}
