<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>--%>
<%--<t:clientPage title="application success">--%>
    <dl>
        <dt>car type</dt>
        <c:if test="${not empty order}">
            <dd class="text-lowercase"><c:out value="${order.carClass}"/></dd>
        </c:if>

        <dt>start location</dt>
        <%--<dd>${order.pickupLocation}</dd>--%>

        <dt>purpose location</dt>
        <%--<dd>${order.dropOffLocation}</dd>--%>

        <dt>driver login</dt>
        <dd><c:out value="${driver.login}" default="unknown"/></dd>
    </dl>
<%--</t:clientPage>--%>
