<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.messages"/>
<fmt:message key="client.clientProfile" var="clientProfile"/>
<t:clientPage title="sending application">
    <p><strong style="color: darkred">${insertOrder}</strong></p>
    <fmt:message key="client.clientProfile" var="clientProfile"/>
    <c:choose>
        <c:when test="${not empty orderServing}"><h3 align=center class="aler alert-info">
            <fmt:message key="${orderServing}"/>
        </h3></c:when>
        <c:otherwise>
            <form class="alert alert-info col-lg-3" style="margin: 50px auto; float: none" id="sendOrderForm"
                  method="post">
                <div class="row">
                    <div class="form-group col-lg-12">
                        <label><fmt:message key="client.carType"/></label>
                        <select class="form-control" name="carType">
                            <option value="VIP">vip</option>
                            <option value="USUALLY"><fmt:message key="client.carType.usually"/></option>
                            <option value="ECONOMY"><fmt:message key="client.carType.economy"/></option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-lg-12">
                        <label class="control-label"><fmt:message key="order.pickupLocation"/></label>
                        <input class="form-control" type="text" name="pickupLocation"/>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-lg-12">
                        <label class="control-label"><fmt:message key="order.dropOffLocation"/></label>
                        <input class="form-control" type="text" name="dropOffLocation"/>
                    </div>
                </div>
                <button id="sendOrder" class="btn btn-success" type="button"><fmt:message
                        key="order.sendOrder"/></button>
            </form>
        </c:otherwise>
    </c:choose>
</t:clientPage>
