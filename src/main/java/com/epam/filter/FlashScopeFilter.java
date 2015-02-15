package com.epam.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class FlashScopeFilter implements Filter {
    private static final String FLASH_SESSION_KEY = "FLASH_SESSION_KEY";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest)req, (HttpServletResponse) resp, chain);
    }

    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session != null){
            Map<String, Object> flashParams = (Map) session.getAttribute(FLASH_SESSION_KEY);
            if (flashParams != null){
                for (Map.Entry<String, Object> flashEntry : flashParams.entrySet()){
                    req.setAttribute(flashEntry.getKey(), flashEntry.getValue());
                }
                session.removeAttribute(FLASH_SESSION_KEY);
            }
        }
//        pre-servlet work
        chain.doFilter(req, resp);
//          post servlet work

        Map flashParams = new HashMap<>();
        Enumeration<String> e = req.getAttributeNames();
        while (e.hasMoreElements()){
            String paramName = e.nextElement();
            if (paramName.startsWith("flash.")){
                Object value = req.getAttribute(paramName);
                paramName = paramName.substring(6, paramName.length());
                flashParams.put(paramName, value);
            }
        }
        if (flashParams.size()>0){
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute(FLASH_SESSION_KEY, flashParams);
        }
    }

    @Override
    public void destroy() {

    }
}
