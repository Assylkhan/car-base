package com.epam.action;

import com.epam.dao.DaoException;
import com.epam.dao.DaoFactory;
import com.epam.entity.Driver;
import com.epam.entity.Order;
import com.epam.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReceivedAppAction implements Action {
    ActionResult result = new ActionResult("receivedApp");
    private static final Logger log = Logger.getLogger(ReceivedAppAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext servletContext = req.getServletContext();
        DaoFactory daoFactory = (DaoFactory) servletContext.getAttribute("daoFactory");
        Driver driver = (Driver) req.getSession().getAttribute("driver");
        Order order = driver.getCurrentOrder();
        if (order == null) {
            OrderService orderService = new OrderService(daoFactory);
            order = orderService.findNotCompletedByDriverId(driver.getId());
            driver.setCurrentOrder(order);
            req.getSession().setAttribute("driver", driver);
        }
        return result;
    }
}
