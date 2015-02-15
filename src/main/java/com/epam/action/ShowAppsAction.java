package com.epam.action;

import com.epam.dao.*;
import com.epam.entity.Order;
import com.epam.service.OrderService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAppsAction implements Action {
    private ActionResult result = new ActionResult("applications");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        DaoFactory factory = DaoFactory.getDaoFactory(DatabaseType.H2);
        OrderService applicationService = new OrderService(factory);
        List<Order> orders = applicationService.findNotCompleted();
        req.setAttribute("orders", orders);
        return result;
    }
}
