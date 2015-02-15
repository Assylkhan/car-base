package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.entity.Client;
import com.epam.entity.Order;
import com.epam.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderResponseAction implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Client client = (Client) req.getSession().getAttribute("client");
        DaoFactory daoFactory = (DaoFactory) req.getServletContext().getAttribute("daoFactory");
        OrderService orderService = new OrderService(daoFactory);
        Order order = orderService.findNotCompletedByClientId(client.getId());
        if (order.getStatus() != Order.OrderStatus.ACCEPTED)
            resp.setStatus(500);
        else
            req.setAttribute("data", order);
        return new ActionResult("json");
    }
}
