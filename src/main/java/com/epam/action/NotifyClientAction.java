package com.epam.action;

import com.epam.dao.DaoException;
import com.epam.dao.DaoFactory;
import com.epam.entity.Driver;
import com.epam.entity.Order.OrderStatus;
import com.epam.service.DriverService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotifyClientAction implements Action {
//    private static final Logger log = Logger.getLogger(NotifyClientAction.class);
//    ActionResult failed = new ActionResult("receivedApp");
ActionResult success = new ActionResult("receivedApp", true);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Driver driver = (Driver) req.getSession().getAttribute("driver");

        DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
        DriverService driverService = new DriverService(daoFactory);
        driver.getCurrentOrder().setStatus(OrderStatus.CLIENT_EXPECTING);
//        try {
        driverService.updateOrderByDriver(driver);
/*        } catch (DaoException e) {
            log.error(e);
            req.setAttribute("failed", "oops, notifying client failed.");
            return failed;
        }*/
        req.getSession().setAttribute("driver", driver);
        return success;
    }
}
