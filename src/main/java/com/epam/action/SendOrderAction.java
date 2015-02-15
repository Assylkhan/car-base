package com.epam.action;

import com.epam.dao.DaoException;
import com.epam.dao.DaoFactory;
import com.epam.entity.Order;
import com.epam.entity.Client;
import com.epam.entity.CarClass;
import com.epam.service.OrderService;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class SendOrderAction implements Action {
    private ActionResult result = new ActionResult("json");
//    private ActionResult failed = new ActionResult("sendOrder");
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext servletContext = req.getSession().getServletContext();
        DaoFactory factory = (DaoFactory)servletContext.getAttribute("daoFactory");
        OrderService orderService = new OrderService(factory);
        Order order = createOrderBean(req);
        try{
            Order insertedOrder = orderService.insert(order);
            req.setAttribute("order", insertedOrder);
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", req.getLocale());
            String message = bundle.getString("client.message.waitingOrderExecution");
            req.setAttribute("data", message);
        } catch (DaoException e){
            resp.setStatus(500);
            req.setAttribute("insertOrder", "sending order failed");
            throw e;
        }
        return result;
    }

    public Order createOrderBean(HttpServletRequest req){
        Order order = new Order();
        HttpSession session = req.getSession();
        Client client = (Client)session.getAttribute("client");
        order.setClient(client);
        order.setCarClass(CarClass.valueOf(req.getParameter("carType")));
        order.setPickupLocation(req.getParameter("pickupLocation"));
        order.setDropOffLocation(req.getParameter("dropOffLocation"));
        order.setStatus(Order.OrderStatus.NOT_SERVED);
        return order;
    }
}
