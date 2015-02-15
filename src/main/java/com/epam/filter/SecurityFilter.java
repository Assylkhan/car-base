package com.epam.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SecurityFilter implements Filter {
    /**
     * set of urls to which not allowed access for unsigned users
     */
    private Set<String> deniedUrls = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        deniedUrls.add("/applications");
        deniedUrls.add("/chooseDriver");
        deniedUrls.add("/dispatcherProfile");
        deniedUrls.add("/clientProfile");
        deniedUrls.add("/driverProfile");
        deniedUrls.add("/sendApp");
        deniedUrls.add("/changeDriverState");
        deniedUrls.add("/receivedApp");
        deniedUrls.add("/successApp");
        deniedUrls.add("/orderServing");
        deniedUrls.add("/receivedApp");
        deniedUrls.add("/acceptOrDeclineApp");
        deniedUrls.add("/startFlight");
        deniedUrls.add("/endFlight");
        deniedUrls.add("/processingFlight");
        deniedUrls.add("/chooseDriver");
        deniedUrls.add("/changeDriverState");
        deniedUrls.add("/showOrders");
        deniedUrls.add("/clientNotification");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        doFilter(((HttpServletRequest) req), ((HttpServletResponse) resp), chain);
    }

    private void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String requestUri = req.getRequestURI();
        if (session.getAttribute("client") != null) {
            if (requestUri.startsWith("/register") || requestUri.startsWith("/login") || requestUri.startsWith("/home")) {
                resp.sendRedirect(req.getContextPath() + "/clientProfile");
                return;
            }
        } else if (session.getAttribute("dispatcher") != null) {
            if (requestUri.startsWith("/register") || requestUri.startsWith("/registerDispatcher") ||
                    requestUri.startsWith("/login") || requestUri.startsWith("/home")) {
                resp.sendRedirect(req.getContextPath() + "/dispatcherProfile");
                return;
            }
        } else if (session.getAttribute("driver") != null) {
            if (requestUri.startsWith("/register") || requestUri.startsWith("/registerDispatcher") ||
                    requestUri.startsWith("/login") || requestUri.startsWith("/home")) {
                resp.sendRedirect(req.getContextPath() + "/dispatcherProfile");
                return;
            }
        } else for (String deniedUrl : deniedUrls) {
            if (requestUri.equals(deniedUrl)) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}