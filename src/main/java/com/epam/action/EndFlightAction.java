package com.epam.action;

import com.epam.dao.*;
import com.epam.entity.Driver;
import com.epam.entity.Order.OrderStatus;
import com.epam.service.DriverService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EndFlightAction implements Action {
    ActionResult success = new ActionResult("receivedApp", true);
//    ActionResult failed = new ActionResult("receivedApp");
//    private static final Logger log = Logger.getLogger(EndFlightAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Driver driver = (Driver) req.getSession().getAttribute("driver");
        DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
        DriverService driverService = new DriverService(daoFactory);
//        try {
            driver.getCurrentOrder().setStatus(OrderStatus.COMPLETED);
            driverService.updateOrderByDriver(driver);
            driver.setCurrentOrder(null);
            req.getSession().setAttribute("driver", driver);
            return success;
       /* } catch (DaoException e) {
            log.error(e);
            req.setAttribute("failed", "occurred internal errors");
            return failed;
        }*/
    }
}
