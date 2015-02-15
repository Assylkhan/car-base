<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:dispatcherPage title="dispatcher registration">
    <form style="margin: 0 auto; float: none;" class="alert alert-info col-lg-3"
          action='<c:url value="/registerDispatcher" />' method="post">
        <t:input type="text" placeholder="first name" name="firstName"
                 value="${param.firstName}" fieldError="${firstNameError}"/>
        <t:input type="text" placeholder="last name" name="lastName"
                 value="${param.lastName}" fieldError="${lastNameError}"/>
        <t:input type="text" placeholder="national id" name="nationalId"
                 value="${param.nationalId}" fieldError="${natIdError}"/>
        <t:input type="text" placeholder="phone" name="phone" value="${param.phone}"/>
        <t:input type="text" placeholder="login" name="login"
                 value="${param.login}" fieldError="${loginError}"/>
        <t:input type="password" placeholder="password" name="password"
                 value="${param.password}" fieldError="${passwordError}"/>
        <t:input type="password" placeholder="confirm password" name="confirmPassword"
                 value="${param.confirmPassword}" fieldError="${confirmError}"/>
        <t:input type="submit" className="btn btn-danger" value="Signup"/>
    </form>
</t:dispatcherPage>
