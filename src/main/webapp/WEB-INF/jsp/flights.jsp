<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:dispatcherPage title="flights">
    <c:choose>

        <c:when test="${not empty orders}">
            <table class="table table-bordered">
                <th>id</th>
                <th>driver login</th>
                <th>client name</th>
                <th>pickup location</th>
                <th>drop off location</th>
                <th>received time</th>
                <th>car class</th>
                <th>cost</th>
                <th>status</th>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td><c:out value="${order.driver.login}"/></td>
                        <td><c:out value="${order.client.firstName}" default="unknown client"/></td>
                        <td>${order.pickupLocation}</td>
                        <td>${order.dropOffLocation}</td>
                        <td>${order.receivedTime}</td>
                        <td>${order.carClass}</td>
                        <td><c:out value="${order.cost}" default="not set"/></td>
                        <td>${order.status}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <h3><strong>There are no orders</strong></h3>
        </c:otherwise>
    </c:choose>
</t:dispatcherPage>
