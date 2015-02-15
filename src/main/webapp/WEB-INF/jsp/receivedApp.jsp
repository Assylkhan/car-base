<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:driverPage>

    <c:if test="${not empty failed}">
        <h3>${failed}</h3>
    </c:if>

    <c:if test="${not empty flightEnded}}">
        <h3>${flightEnded}</h3>
    </c:if>

    <c:choose>
        <c:when test="${not empty driver.currentOrder}">
            <h3 align="center" class="alert alert-info">flight process</h3>
            <ol class="progress-tracker">
                <li class="step"><span class="step-name">Accept</span></li>
                <li class="step"><span class="step-name">client expecting</span></li>
                <li class="step"><span class="step-name">start</span></li>
                <li class="step"><span class="step-name">end</span></li>
            </ol>
            <dl>
                <h2>${driver.currentOrder.status}</h2>
                <dt>client's first name</dt>
                <dd><c:out value="${driver.currentOrder.client.firstName}" default="unknown"/></dd>

                <dt>start location</dt>
                <dd>${driver.currentOrder.pickupLocation}</dd>

                <dt>end location</dt>
                <dd>${driver.currentOrder.dropOffLocation}</dd>

                <dt>sent receivedTime</dt>
                <dd>${driver.currentOrder.receivedTime}</dd>
            </dl>
            <c:choose>
                <c:when test="${driver.currentOrder.status == 'NOT_SERVED'}">
                    <form id="accept" action='<c:url value="/acceptOrDeclineApp"/>' method="post">
                        <input class="btn btn-success" type="submit" name="act" value="accept"/>
                        <input class="btn btn-danger" type="submit" name="act" value="decline"/>
                    </form>
                </c:when>
                <c:when test="${driver.currentOrder.status == 'ACCEPTED'}">
                    <form id="clientExpecting" action='<c:url value="/notifyClient"/>' method="post">
                        <input class="btn btn-success" type="submit" name="act" value="start"/>
                    </form>
                </c:when>
                <c:when test="${driver.currentOrder.status == 'CLIENT_EXPECTING'}">
                    <form id="start" action='<c:url value="/startFlight"/>' method="post">
                        <input class="btn btn-success" type="submit" name="act" value="start"/>
                    </form>
                </c:when>
                <c:when test="${driver.currentOrder.status == 'IN_PROCESS'}">
                    <form id="end" action='<c:url value="/endFlight"/>' method="post">
                        <input class="btn btn-success" type="submit" name="act" value="end"/>
                    </form>
                </c:when>
            </c:choose>
        </c:when>
        <c:otherwise>
            <h2 align=center><strong>currently you don't have any order.</strong></h2><br/><br/>
        </c:otherwise>
    </c:choose>

</t:driverPage>
