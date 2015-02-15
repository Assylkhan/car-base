<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:driverPage>

    <c:if test="${not empty internalError}">
        <h3>${internalError}</h3>
    </c:if>
    <h2 align=center>You have a scheduled flight</h2>
    <dl>
        <dt>client's first name</dt>
        <dd><c:out value="${order.client.firstName}" default="unknown"/></dd>

        <dt>start location</dt>
        <dd>${order.pickupLocation}</dd>

        <dt>end location</dt>
        <dd>${order.dropOffLocation}</dd>

        <dt>destination</dt>
        <dd>${order.carClass}</dd>

        <dt>sent receivedTime</dt>
        <dd>${order.receivedTime}</dd>
    </dl>
    <c:set var="enabled" value="enabled"/>

    <form action='<c:url value="/startFlight"/>' method="post">
        <input class="btn btn-success" type="submit" ${enabled} name="act" value="start"/>
    </form>
</t:driverPage>
