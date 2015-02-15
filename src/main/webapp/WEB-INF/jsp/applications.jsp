<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:dispatcherPage title="orders">
    <c:if test="${not empty choosedDriver}">
        <h3><strong class="alert-success">${chosenDriver}</strong></h3>
    </c:if>
    <c:choose>
        <c:when test="${not empty orders}">
            <table class="table table-striped">
                <th>id</th>
                <th>client name</th>
                <th>pickup location</th>
                <th>drop off location</th>
                <th>car type</th>
                <th>receivedTime</th>
                <th></th>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td><c:out value="${order.client.firstName}" default="unknown client"/></td>
                        <td>${order.pickupLocation}</td>
                        <td>${order.dropOffLocation}</td>
                        <td>${order.carClass}</td>
                        <td>${order.receivedTime}</td>
                        <td>
                            <form action='<c:url value="/availableDrivers"/>'>
                                <input type="hidden" name="selectedOrderId" value="${orderId}"/>
                                <button ${buttonState} class="btn btn-danger" type="submit">serve</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <h3><strong>There are no orders</strong></h3>
        </c:otherwise>
    </c:choose>
</t:dispatcherPage>
