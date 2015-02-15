<%@tag description="Client Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title"%>
<t:genericPage title="${title}" role="driver">
    <jsp:attribute name="header">
        <div id="pageHeader" class="navbar navbar-inverse">
            <div class="container-fluid">
                <ul class="list-unstyled pull-right">
                    <c:choose>
                        <c:when test="${not empty sessionScope.driver}">
                            <li><strong><a href="<c:url value='/receivedApp'/> ">received application</a></strong></li>
                            <li><strong><a href="<c:url value='/driverProfile'/>">profile</a></strong></li>
                            <li><strong><a href="<c:url value='/logout'/>">logout</a></strong></li>
                        </c:when>
                        <c:otherwise>
                            <li><strong><a href="<c:url value='/login'/>">login</a></strong></li>
                            <li><strong><a href="<c:url value='/register'/>">register</a></strong></li>
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