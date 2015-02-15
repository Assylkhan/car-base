<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:driverPage title="flight in process">
    <c:if test="${not empty errorMessage}">
        <h3 align=center>${errorMessage}</h3>
    </c:if>
    <p><strong style="color: #9DB668">your flight in process</strong></p>
    <dl>
        <dt>client's first name</dt>
        <dd><c:out value="${driver.application.client.firstName}" default="unknown"/></dd>

        <dt>start location</dt>
        <dd>${driver.application.pickupLocation}</dd>

        <dt>end location</dt>
        <dd>${driver.application.dropOffLocation}</dd>

        <dt>destination</dt>
        <dd>${driver.application.destination}</dd>

        <dt>sent receivedTime</dt>
        <dd>${driver.application.receivedTime}</dd>
    </dl>
    <br/><br/>

    <p>To end your flight press
        <form action='<c:url value="/endFlight"/>' method="post">
            <input type="hidden" name="flightStatus" value="ended"/>
            <t:input type="submit" value="end flight"/>
        </form>
    </p>
</t:driverPage>
