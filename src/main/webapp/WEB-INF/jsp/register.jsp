<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.messages"/>
<fmt:message var="registration" key="registration.registration"/>
<t:clientPage title="${registration}">
    <fmt:message var="firstName" key="user.firstName"/>
    <fmt:message var="lastName" key="user.lastName"/>
    <fmt:message var="login" key="user.login"/>
    <fmt:message var="password" key="user.password"/>
    <fmt:message var="confirmPassword" key="registration.confirmPassword"/>
    <fmt:message var="signup" key="registration.signup"/>
    <form class="alert alert-info col-lg-4" style="padding-top: 35px; margin: 70px auto; float: none"
          action="${pageContext.request.contextPath}/register" method="post">
        <t:input type="text" placeholder="${firstName}" name="firstName"
                 value="${param.firstName}" fieldError="${firstNameError}"/>
        <t:input type="text" placeholder="${lastName}" name="lastName"
                 value="${param.lastName}" fieldError="${lastNameError}"/>
        <t:input type="text" placeholder="${login}" name="login"
                 value="${param.login}" fieldError="${loginError}"/>
        <t:input type="password" placeholder="${password}" name="password"
                 value="${param.password}" fieldError="${passwordError}"/>
        <t:input type="password" placeholder="${confirmPassword}" name="confirmPassword"
                 value="${param.confirmPassword}" fieldError="${confirmError}"/>
        <t:input type="submit" className="btn btn-warning" value="${signup}"/>
    </form>
</t:clientPage>
