package com.epam.action;

import com.epam.dao.DaoException;
import com.epam.dao.DaoFactory;
import com.epam.entity.Dispatcher;
import com.epam.service.DispatcherService;
import com.epam.util.HashGenerator;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DispatcherLoginAction implements Action {
    ActionResult loginAgain = new ActionResult("dispatcherLogin");
    ActionResult result = new ActionResult("dispatcherProfile", true);
    public static final Logger logger = Logger.getLogger(DispatcherLoginAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String generatedPassword = HashGenerator.passwordToHash(password);
        ServletContext servletContext = req.getSession().getServletContext();
        DaoFactory daoFactory = (DaoFactory) servletContext.getAttribute("daoFactory");
        DispatcherService dispatcherService = new DispatcherService(daoFactory);
        Dispatcher dispatcher = dispatcherService.findByCredentials(login, generatedPassword);
        if (dispatcher == null) {
            req.setAttribute("loginError", "login.loginOrPasswordIncorrect");
            return loginAgain;
        }
        HttpSession session = req.getSession();
        session.setAttribute("dispatcher", dispatcher);

        return result;

        /*Cookie cookie = new Cookie("sessionId", session.getId());
        cookie.setMaxAge(24 * 60 * 60);
        resp.addCookie(cookie);*/
    }
}
