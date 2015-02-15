package com.epam.action;

import com.epam.dao.*;
import com.epam.entity.Client;
import com.epam.entity.Comment;
import com.epam.service.ClientService;
import com.epam.service.CommentService;
import com.epam.util.HashGenerator;
import com.epam.validation.UserValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RegisterClientAction implements Action {
    ActionResult registerAgain = new ActionResult("register");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        boolean valid = UserValidator.validateClient(req);
        if (!valid) return registerAgain;
        DaoFactory factory = DaoFactory.getDaoFactory(DatabaseType.H2);
        Client client = getClientBean(req);
        ClientService clientService = new ClientService(factory);
        Client insertedClient = clientService.insert(client);
        if (insertedClient == null) return registerAgain;
        return new ClientLoginAction().execute(req, resp);
    }

    private Client getClientBean(HttpServletRequest req) {
        Client client = new Client();
        client.setLogin(req.getParameter("login"));
        String generatedPassword = HashGenerator.passwordToHash(req.getParameter("password"));
        client.setPassword(req.getParameter(generatedPassword));
        client.setFirstName(req.getParameter("firstName"));
        client.setLastName(req.getParameter("lastName"));
        return client;
    }
}
