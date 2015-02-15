package com.epam.action;

public class ActionResult {
    public final String view;
    public final boolean redirect;

    public ActionResult(String page, boolean redirect) {
        this.view = page;
        this.redirect = redirect;
    }

    public ActionResult(String page) {
        this.view = page;
        this.redirect = false;
    }

    public String getView() {
        return view;
    }

    public Boolean isRedirect(){
        return redirect;
    }
}
