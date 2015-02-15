package com.epam.action;

import com.epam.dao.*;
import com.epam.entity.Driver;
import com.epam.entity.Order.OrderStatus;
import com.epam.service.DriverService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartFlightAction implements Action {
//    private static Logger log = Logger.getLogger(StartFlightAction.class);
    ActionResult success = new ActionResult("receivedApp", true);
//    ActionResult failed = new ActionResult("receivedApp");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
        Driver driver = (Driver) req.getSession().getAttribute("driver");
//        try {
        DriverService driverService = new DriverService(daoFactory);
        driver.getCurrentOrder().setStatus(OrderStatus.IN_PROCESS);
        driverService.updateOrderByDriver(driver);
        req.getSession().setAttribute("driver", driver);
        return success;
        /*} catch (DaoException e) {
            log.error(e);
            req.setAttribute("failed", "occurred an internal server error");
            return failed;
        }*/
    }
}
