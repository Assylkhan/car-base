<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:dispatcherPage title=" dispatcher login">
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="i18n.messages"/>
    <fmt:message var="login" key="user.login"/>
    <fmt:message key="user.password" var="password"/>
    <fmt:message key="login.button.login" var="buttonLogin"/>

    <form class="alert alert-info col-lg-3" style="margin: 100px auto; float: none"
          action='<c:url value="/dispatcherLogin"/>' method="post">
        <c:if test="${not empty loginError}">
            <strong class="alert-danger">
                <fmt:message key="${loginError}"/>
            </strong><br/></c:if>
        <br/>
        <t:input name="login" type="text" value="${param.login}" placeholder="${login}"/>
        <t:input name="password" type="password" value="${param.password}" placeholder="${password}"/>
        <t:input type="submit" value="${buttonLogin}" className="btn btn-warning"/>
    </form>
</t:dispatcherPage>
