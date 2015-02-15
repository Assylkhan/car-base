package com.epam.action;

import com.epam.dao.*;
import com.epam.entity.*;
import com.epam.service.UserService;
import com.epam.util.HashGenerator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientLoginAction implements Action {
    ActionResult loginAgain = new ActionResult("login");
    ActionResult success = new ActionResult("clientProfile", true);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String generatedPassword = HashGenerator.passwordToHash(password);
        ServletContext servletContext = req.getSession().getServletContext();
        DaoFactory daoFactory = (DaoFactory) servletContext.getAttribute("daoFactory");
        UserService userService = new UserService(daoFactory);
        Client client = (Client)userService.findByCredentials(login, generatedPassword, Role.CLIENT);
        if (client == null) {
            req.setAttribute("loginError", "login.loginOrPasswordIncorrect");
            return loginAgain;
        }
        req.getSession().setAttribute("client", client);
        return success;

        /*Cookie cookie = new Cookie("sessionId", session.getId());
        cookie.setMaxAge(24 * 60 * 60);
        resp.addCookie(cookie);*/
    }
}
