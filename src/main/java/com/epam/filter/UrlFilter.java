package com.epam.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest)req, (HttpServletResponse) resp, chain);
    }
    private void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        if (requestURI != null) {
            if (requestURI.startsWith("/static") || requestURI.startsWith("/image") || requestURI.startsWith("/webjars")){
                chain.doFilter(req, resp);
                return;
            }
        }
        req.getRequestDispatcher("/do"+requestURI).forward(req, resp);
    }
    @Override
    public void destroy() {

    }
}