<%@tag description="Dispatcher Page Template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" %>
<t:genericPage title="${title}" role="dispatcher">
    <jsp:attribute name="header">
        <div id="pageHeader" class="navbar navbar-inverse">
            <div class="container-fluid">
                <ul class="list-unstyled pull-right">
                    <c:choose>
                        <c:when test="${not empty sessionScope.dispatcher }">
                            <li><strong><a href="<c:url value='/orderServing'/>">order serving</a></strong></li>
                            <li><strong><a href="<c:url value='/showOrders'/>">show orders</a></strong></li>
                            <li class="dropdown" id="menu1">
                                <a class="dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown"
                                   href="javascript:void(0)">
                                    <c:out value="${dispatcher.firstName}" default="no dispatcher"/>
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu" role="menu">
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1" href="<c:url value='/dispatcherProfile'/>">profile</a>
                                    </li>
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1" href="<c:url value='/logout'/>">logout</a>
                                    </li>
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><strong><a href="<c:url value='/dispatcherLogin'/>">login</a></strong></li>
                            <li><strong><a href="<c:url value='/registerDispatcher'/>">register</a></strong></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <div id="pageFooter" class="navbar navbar-inverse navbar-fixed-bottom">
            <div class="container-fluid">
                <ul class="list-unstyled pull-right">
                    <li><strong><a href="#">copyright</a></strong></li>
                    <li><strong><a href="#">about</a></strong></li>
                </ul>
            </div>
        </div>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:genericPage>