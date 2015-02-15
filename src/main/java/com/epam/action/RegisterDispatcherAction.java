package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.dao.DatabaseType;
import com.epam.entity.Dispatcher;
import com.epam.service.DispatcherService;
import com.epam.util.HashGenerator;
import com.epam.validation.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterDispatcherAction implements Action {
    ActionResult registerAgain = new ActionResult("registerDispatcher");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        boolean valid = UserValidator.validateEmployee(req);
        if (!valid) return registerAgain;
        DaoFactory factory = DaoFactory.getDaoFactory(DatabaseType.H2);
        Dispatcher dispatcher = getDispatcherBean(req);
        DispatcherService service = new DispatcherService(factory);
        Dispatcher insertedDispatcher = service.insert(dispatcher);
        if (insertedDispatcher == null) return registerAgain;
        return new ClientLoginAction().execute(req, resp);
    }

    private Dispatcher getDispatcherBean(HttpServletRequest req){
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setFirstName(req.getParameter("firstName"));
        dispatcher.setLastName(req.getParameter("lastName"));
        dispatcher.setLogin(req.getParameter("login"));
        String generatedPassword = HashGenerator.passwordToHash(req.getParameter("password"));
        dispatcher.setPassword(generatedPassword);
        dispatcher.setNationalId(req.getParameter("nationalId"));
        dispatcher.setPhone(req.getParameter("phone"));
        return dispatcher;
    }
}
