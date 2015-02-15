<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:dispatcherPage title="drivers">
    <p><strong class="alert-danger">${chosenDriver}</strong></p>

    <form id="serveOrderForm" action='<c:url value="/chooseDriver"/>' method="post">
        <h3>choose a necessary driver</h3>
        <select name="driverId" size="5" class="form-control">
            <c:forEach items="${drivers}" var="driver">
                <option style="color: #000;"
                        value="${driver.id}">
                    <dl class="dl-horizontal">
                            ${driver.id}.
                        first name: ${driver.firstName} |
                        last name: ${driver.lastName} |
                        phone: ${driver.phone} |
                        available: ${driver.available} |
                        current location: <c:out value="${driver.currentLocation}" default="unknown"/>
                    </dl>
                </option>
            </c:forEach>
        </select>

        <h3>choose a necessary application</h3>

        <table id="orders" class="scroll table-bordered">
            <thead>

            <tr>
                <th>id</th>
                <th>client name</th>
                <th>pickup location</th>
                <th>drop off location</th>
                <th>car type</th>
                <th>receivedTime</th>
            </tr>

            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr id="${order.id}">
                    <td>${order.id}</td>
                    <td><c:out value="${order.client.firstName}" default="unknown client"/></td>
                    <td>${order.pickupLocation}</td>
                    <td>${order.dropOffLocation}</td>
                    <td>${order.carClass}</td>
                    <td>${order.receivedTime}</td>
                </tr>
            </c:forEach>
            </tbody>

        </table>

        <input name="orderId" id="orderId" type="hidden"/>
        <br/><br/>

        <t:input name="cost" fieldError="${costError}" label="set a cost"/>

        <button id="serveOrder" type="button" class="btn btn-success">submit</button>
    </form>

</t:dispatcherPage>
