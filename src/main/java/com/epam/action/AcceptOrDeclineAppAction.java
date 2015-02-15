package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.entity.Driver;
import com.epam.entity.Order.OrderStatus;
import com.epam.service.DriverService;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcceptOrDeclineAppAction implements Action {
    private static Logger log = Logger.getLogger(StartFlightAction.class);
    ActionResult success = new ActionResult("receivedApp", true);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext servletContext = req.getServletContext();
        DaoFactory daoFactory = (DaoFactory) servletContext.getAttribute("daoFactory");
        String act = req.getParameter("act");
        Driver driver = (Driver) req.getSession().getAttribute("driver");
        DriverService driverService = new DriverService(daoFactory);
        if (act.equals("accept")) {
            driver.getCurrentOrder().setStatus(OrderStatus.ACCEPTED);
            driverService.updateOrderByDriver(driver);
            log.info("accepted order by driver");
        } else if (act.equals("decline")) {
            driver.getCurrentOrder().setStatus(OrderStatus.NOT_SERVED);
            driver.getCurrentOrder().setDriver(null);
            driverService.updateOrderByDriver(driver);
            driver.setCurrentOrder(null);
            log.info("declined order by driver");
        }
        req.getSession().setAttribute("driver", driver);

        return success;
    }
}
