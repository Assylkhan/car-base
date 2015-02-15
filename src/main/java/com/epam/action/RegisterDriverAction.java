package com.epam.action;

import com.epam.dao.DaoFactory;
import com.epam.dao.DatabaseType;
import com.epam.entity.Driver;
import com.epam.service.DriverService;
import com.epam.util.HashGenerator;
import com.epam.validation.UserValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterDriverAction implements Action {
    ActionResult registerAgain = new ActionResult("registerDriver");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        boolean valid = UserValidator.validateEmployee(req);
        if (!valid) return registerAgain;
        DaoFactory factory = DaoFactory.getDaoFactory(DatabaseType.H2);
        Driver driver = getDriverBean(req);
        DriverService service = new DriverService(factory);
        Driver insertedDriver = service.insert(driver);
        if (insertedDriver == null) return registerAgain;
        return new ClientLoginAction().execute(req, resp);
    }

    private Driver getDriverBean(HttpServletRequest req) {
        Driver driver = new Driver();
        driver.setFirstName(req.getParameter("firstName"));
        driver.setLastName(req.getParameter("lastName"));
        driver.setLogin(req.getParameter("login"));
        String generatedPassword = HashGenerator.passwordToHash(req.getParameter("password"));
        driver.setPassword(generatedPassword);
        driver.setNationalId(req.getParameter("nationalId"));
        driver.setPhone(req.getParameter("phone"));
        return driver;
    }
}
