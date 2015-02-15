package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.entity.Driver;
import com.epam.entity.Order;
import com.epam.service.DriverService;
import com.epam.service.OrderService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowOrderServingAction implements Action {
    ActionResult result = new ActionResult("orderServing");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext servletContext = req.getServletContext();
        DaoFactory daoFactory = (DaoFactory) servletContext.getAttribute("daoFactory");
        DriverService driverService = new DriverService(daoFactory);
        OrderService orderService = new OrderService(daoFactory);
        String orderId = req.getParameter("selectedOrderId");
        req.setAttribute("selectedOrderId", orderId);
        List<Order> orders = orderService.findNotCompleted();
        List<Driver> drivers = driverService.findAvailableDrivers();
        req.setAttribute("drivers", drivers);
        req.setAttribute("orders", orders);
        return result;
    }
}
