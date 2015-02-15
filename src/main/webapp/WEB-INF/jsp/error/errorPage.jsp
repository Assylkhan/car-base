<%@page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Error/exception information</title>
    <link type="text/css" rel="stylesheet" href='<c:url value="webjars/bootstrap/3.3.1/css/bootstrap.css"/>'>
</head>
<body bgcolor="f0f0f0">
<div class="container-fluid">
    <h1 align=center class="alert alert-danger">oops, error!</h1>

    <h3 align=center>Unfortunately an unexpected error has occurred. Below you can find the error details.</h3>

    <h2>Details</h2>
    <c:if test="${not empty requestScope['javax.servlet.error.status_code']}">
        <p>The status code: ${requestScope['javax.servlet.error.status_code']}</p>
    </c:if>
    <%--<p>Message ${exception}</p>--%>
    <ul>
        <li>Timestamp:
            <fmt:formatDate value="${date}" type="both" dateStyle="long" timeStyle="long"/></li>
        <c:if test="${not empty requsetUri}">
            <li>Action: ${requestUri}</li>
        </c:if>
        <c:if test="${not empty throwable}">
            <li>Exception: ${throwable}</li>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <li>Message: ${errorMessage}</li>
        </c:if>
        <c:if test="${not empty statusCode}">
            <li>Status code: ${statusCode}</li>
        </c:if>
        <%--${header['user-agent']}--%>
    </ul>

</div>
</body>
</html>
