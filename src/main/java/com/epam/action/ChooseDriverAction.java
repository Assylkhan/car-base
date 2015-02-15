package com.epam.action;

import com.epam.dao.*;
import com.epam.entity.Driver;
import com.epam.entity.Order;
import com.epam.service.OrderService;
import com.epam.validation.InputValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ChooseDriverAction implements Action {
    ActionResult result = new ActionResult("drivers", true);
    //    ActionResult failed = new ActionResult("drivers");
//    private static final Logger logger = Logger.getLogger(ChooseDriverAction.class);
    private static String cost;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
        if (!validateCost(req)) {
            req.setAttribute("costError", "invalid cost!");
            return result;
        }
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.forEach((Integer value) -> System.out.println(value));
        Order order = getOrderBean(req);
        OrderService orderService = new OrderService(daoFactory);
        orderService.entrustToDriver(order);
        req.setAttribute("flash.chosenDriver", "task has been given to driver successfully!");

        return result;
    }

    private Order getOrderBean(HttpServletRequest req) {
        Long driverId = Long.valueOf(req.getParameter("driverId"));
        Long orderId = Long.valueOf(req.getParameter("orderId"));
        Driver driver = new Driver();
        driver.setId(driverId);
        driver.setAvailable(false);
        Order order = new Order();
        order.setId(orderId);
        order.setCost(new BigDecimal(cost));
        order.setDriver(driver);
        return order;
    }

    private boolean validateCost(HttpServletRequest req) {
        cost = req.getParameter("cost");
        if (!InputValidator.money(cost)) {
            return false;
        }
        return true;
    }
}
