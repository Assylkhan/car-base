package com.epam.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLocaleAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String header = req.getHeader("referer");
        header = header.substring(header.lastIndexOf("/") + 1);
        ActionResult currentPage = new ActionResult(header, true);
        String language = req.getParameter("language");
        HttpSession session = req.getSession();
//        if (language.length() > 2)
//            session.setAttribute("language", Locale.forLanguageTag(language.replace("_", "-")));
//        else
        session.setAttribute("locale", new Locale(language));

        return currentPage;
    }
}
