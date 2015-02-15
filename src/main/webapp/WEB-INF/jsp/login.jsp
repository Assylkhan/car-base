<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:clientPage title="login">
    <fmt:setLocale value="${locale}"/>
    <fmt:setBundle basename="i18n.messages"/>
    <fmt:message key="user.login" var="login"/>
    <fmt:message key="user.password" var="password"/>
    <fmt:message key="login.button.login" var="buttonLogin"/>
    <form class="alert alert-info col-lg-3" style="margin: 70px auto; float: none" action='<c:url value="/login"/>' method="post">
        <strong style="color: darkred">${loginError}</strong> <br/> <br/>

        <t:input type="text" placeholder="${login}" value="${param.login}" name="login"/>
        <t:input type="password" placeholder="${password}" value="${param.password}" name="password"/>
        <t:input className="btn btn-warning" value="${buttonLogin}" type="submit"/>
    </form>
</t:clientPage>
