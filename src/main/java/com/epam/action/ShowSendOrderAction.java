package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.entity.Client;
import com.epam.entity.Order;
import com.epam.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowSendOrderAction implements Action {
    ActionResult result = new ActionResult("sendOrder");
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Client client = (Client)req.getSession().getAttribute("client");
        DaoFactory daoFactory = (DaoFactory)req.getServletContext().getAttribute("daoFactory");
        OrderService orderService = new OrderService(daoFactory);
        Order order = orderService.findNotCompletedByClientId(client.getId());
        if (order != null){
            req.setAttribute("orderServing", "order.message.serving");
        }
        return result;
    }
}
