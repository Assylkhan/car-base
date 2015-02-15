package com.epam.servlet;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandler extends HttpServlet {
    private static final Logger log = Logger.getLogger(ErrorHandler.class);
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Analyze the servlet exception
        Throwable throwable = (Throwable)
                req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Integer statusCode = (Integer)
                req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String servletName = (String)
                req.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        String errorMessage = (String)
                req.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String requestUri = (String)
                req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        req.setAttribute("throwable", throwable);
        req.setAttribute("statusCode", statusCode);
        req.setAttribute("servletName", servletName);
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("requestUri", RequestDispatcher.ERROR_EXCEPTION);
         // value of above const - "javax.servlet.error.request_uri"
        if (throwable != null) {
            log.error("occurred exception: ", throwable);
        }
        if (requestUri != null) {
            log.error("status code of error: " + statusCode);
        }
        if (servletName != null) {
            log.error("error servlet name: " + servletName);
        }
        if (errorMessage != null){
            log.error("error message: " + errorMessage);
        }
        if (requestUri != null) {
            log.error("error request uri: " + requestUri);
        }

        req.getRequestDispatcher("/WEB-INF/jsp/error/errorPage.jsp").forward(req, resp);
    }
}