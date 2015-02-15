package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.entity.Driver;
import com.epam.service.DriverService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeDriverStateAction implements Action {
    ActionResult result = new ActionResult("driverProfile", true);
//    ActionResult failed = new ActionResult("changeDriverState");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
        DriverService driverService = new DriverService(daoFactory);
        String checkBox = req.getParameter("available");
        boolean available = false;
        if (checkBox != null) available = true;
        String location = req.getParameter("location");
        Driver driver = (Driver) req.getSession().getAttribute("driver");
        driver.setAvailable(available);
        driver.setCurrentLocation(location);
        driverService.updateState(driver);
        req.getSession().setAttribute("driver", driver);
        req.setAttribute("flash.changeResult", "successfully changed");
        return result;
    }
}
