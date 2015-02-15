<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${locale == 'en' ? 'en_US' : 'ru_RU'}"/>
<fmt:setBundle basename="i18n.messages"/>
<fmt:message key="client.clientProfile" var="clientProfile"/>
<t:clientPage title="client profile">

    <dl class="dl">
        <p>${insertApp}</p><br/>

        <dt><fmt:message key="user.firstName"/></dt>
        <dd>${client.firstName}</dd>

        <dt><fmt:message key="user.lastName"/></dt>
        <dd>${client.lastName}</dd>

        <dt><fmt:message key="user.login"/></dt>
        <dd>${client.login}</dd>

        <dt><fmt:message key="client.profile.bill"/></dt>
        <fmt:message var="tenge" key="client.bill.currency"/>
        <dd title="${tenge}">${client.bill} <fmt:message key="client.bill.currency"/></dd>
        <%--<fmt:formatNumber value="${client.bill}" type="currency"/>--%>
    </dl>

</t:clientPage>
