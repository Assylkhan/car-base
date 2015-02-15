<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.messages"/>
<fmt:message key="client.clientNotifications" var="clientNotifications"/>
<t:clientPage title="${clientNotifications}">
    <c:choose>
        <c:when test="${empty order}">
            <h2 align=center><fmt:message key="client.message.doNotHaveNotifications"/></h2>
        </c:when>
        <c:when test="${not empty order}">
            <c:if test="${order.status != 'CLIENT_EXPECTING'}">
                <h2 align=center><fmt:message key="client.message.waitCab"/> </h2>
            </c:if>
            <c:if test="${order.status == 'CLIENT_EXPECTING'}">
                <h2 align=center class="alert alert-info"><fmt:message key="driverWaiting"/></h2>
                <dl>
                    <dt><fmt:message key="driver.carImage"/></dt>
                    <dd><img src="/static/image/CaddyCTS_V.jpg" alt="caddillac"/></dd>

                    <dt><fmt:message key="driver.carGovNumber"/></dt>
                    <dd>${order.driver.govNumber}</dd>

                    <dt><fmt:message key="driver.carModel"/></dt>
                    <dd>${order.driver.carModel}</dd>

                    <dt><fmt:message key="driver.carType"/></dt>
                    <dd>${order.driver.carClass}</dd>
                </dl>
            </c:if>
        </c:when>
    </c:choose>

</t:clientPage>
