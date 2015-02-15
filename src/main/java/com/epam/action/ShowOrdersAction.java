package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.entity.Order;
import com.epam.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowOrdersAction implements Action {
    ActionResult actionResult = new ActionResult("flights");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
        OrderService orderService = new OrderService(daoFactory);
        List<Order> orders = orderService.findAll();
        req.setAttribute("orders", orders);
        return actionResult;
    }
}
