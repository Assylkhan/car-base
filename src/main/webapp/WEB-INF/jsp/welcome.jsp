<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.messages"/>
<fmt:message var="welcome" key="client.pageTitle.welcome"/>
<t:clientPage title="${welcome}">
    <div class="alert-info alert">
        <h2 align=center><fmt:message key="client.welcome.message.taxiService"/><br/>
        <fmt:message key="client.welcome.orderTaxi"/></h2>
    </div>
    <div class="row">
        <div class="col-xs-6 col-md-3" style="margin: auto; float: none">
            <a href="#" class="thumbnail">
                <img src="/static/image/taxi.jpg" alt="taxi service">
            </a>
        </div>
    </div>
    <h3 align=center><fmt:message key="client.message.loginForOrder"/></h3>
</t:clientPage>
