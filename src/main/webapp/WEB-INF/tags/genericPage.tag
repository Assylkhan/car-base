<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="title" %>
<%@attribute name="role" %>
<c:set var="locale"
       value="${not empty param.language ? param.language : not empty locale ? locale : pageContext.request.locale}"
       scope="session"/>
<html lang="${locale}">
<head>
    <link rel="stylesheet" href='<c:url value="/webjars/bootstrap/3.3.1/css/bootstrap.css"/>'>
    <script src='<c:url value="/webjars/jquery/2.1.3/jquery.js"/>'></script>
    <script src='<c:url value="/webjars/bootstrap/3.3.1/js/bootstrap.js"/>'></script>
    <script src='<c:url value="/static/js/Application.js"/>'></script>

    <link rel="stylesheet" href="/static/css/main.css">
    <%--<link rel="icon" href='<c:url value="/static/image/logoTitle.ico"/>' type="image/x-icon">--%>
    <title>${title}</title>
</head>
<body id="${role}">
<div class="own-container" id="parent-wrapper">

    <jsp:invoke fragment="header"/>

    <div id="wrapper" class="row">
        <jsp:doBody/>
    </div>
    <%--<div class="push"></div>--%>

    <jsp:invoke fragment="footer"/>
</div>
</body>
</html>
