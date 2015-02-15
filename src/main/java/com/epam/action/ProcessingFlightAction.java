package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.entity.Driver;
import com.epam.entity.Order;
import com.epam.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessingFlightAction implements Action {
    ActionResult processingFlight = new ActionResult("processingFlight");
    ActionResult startFlight = new ActionResult("startFlight", true);
    ActionResult receivedApp = new ActionResult("receivedApp", true);
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Driver driver = (Driver) req.getSession().getAttribute("driver");
        DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
        Order order = driver.getCurrentOrder();
        if (order == null){
            OrderService orderService = new OrderService(daoFactory);
//            order = orderService.findScheduledByApplicationId(driver.getId());
            driver.setCurrentOrder(order);
            req.getSession().setAttribute("driver", driver);
            if (order == null) return receivedApp;
        }
        switch (order.getStatus()){
            case NOT_SERVED:
                return receivedApp;
            case ACCEPTED:
                return startFlight;
            case IN_PROCESS:
                return processingFlight;
        }
        return receivedApp;
    }
}
