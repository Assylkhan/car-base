<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:driverPage title="changing current state">
    <form style="margin: 70px auto; float: none" class="alert alert-info col-lg-3" action='<c:url value="/changeDriverState"/>' method="post">
        <strong style="color: darkred">${changeResult}</strong><br/>

        <div class="row">
            <div class="form-group col-lg-10 checkbox">
                <label>
                    <input type="checkbox" name="available"/> Are you currently available
                </label>
            </div>
        </div>

        <t:input type="text" name="location" placeholder="current location"/>
        <t:input type="submit" value="update" className="btn btn-info"/>
    </form>
</t:driverPage>
