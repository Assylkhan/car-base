<%@tag description="Client Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.messages"/>
<t:genericPage title="${title}" role="client">
    <jsp:attribute name="header">
        <div class="row aboveHeader container" style="margin:auto;">
            <div class="row">
                <div class="pull-left" style="margin-left: 50px">
                    <h2 style="color: #F89406">Karaganda Taxi Service</h2>

                    <h3 class="alert alert-danger" align=center>WEB ORDER <br/>
                        <small>high quality service</small>
                    </h3>
                </div>
                <div class="pull-left" style="margin-left: 150px">
                    <h1 style="color: #F89406"><strong>777-777</strong></h1>
                </div>
            </div>

            <div class="row" style="margin-bottom: -20px">
                <div class="thumbnail">
                    <img style="width: 1200px !important; height: 200px" src="/static/image/taxiForHeader.jpg"
                         alt="taxi image"/>
                </div>
            </div>


        </div>
        <header class="header">
            <div class="navbar" style="">
                <div class="container-fluid">
                    <ul class="list-unstyled pull-left">
                        <li>
                            <form style="margin: 0" action='<c:url value="/changeLocale"/>' method="post">
                                <select autocomplete="off" id="language" name="language" onchange="submit()">
                                    <option value="en" ${locale == 'en_US' || locale=='en' ? 'selected' : ''}>English
                                    </option>
                                    <option value="ru" ${locale == 'ru_RU' || locale=='ru' ? 'selected' : ''}>Русский
                                    </option>
                                        <%--<option value="es" ${language == 'es' ? 'selected' : ''}>Español</option>--%>
                                </select>
                            </form>
                        </li>
                    </ul>
                    <ul class="list-unstyled pull-right">
                        <c:choose>
                            <c:when test="${not empty sessionScope.client}">
                                <li>
                                    <strong><a href='<c:url value="/clientNotification"/>'><fmt:message
                                            key="client.notifications"/>
                                    </a></strong>
                                </li>
                                <li><strong><a href="<c:url value='/sendOrder'/>">
                                    <fmt:message key="client.orderTaxi"/>
                                </a></strong></li>
                                <li class="dropdown" id="menu1">
                                    <a class="dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown"
                                       href="javascript:void(0)">
                                        <c:out value="${client.login}"/>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu" role="menu" style="left: -30px;">
                                        <li role="presentation">
                                            <a role="menuitem" tabindex="-1"
                                               href="<c:url value='/clientProfile'/>">
                                                <fmt:message key="user.profile"/>
                                            </a>
                                        </li>
                                        <li role="presentation">
                                            <a role="menuitem" tabindex="-1" href="<c:url value='/logout'/>">
                                                <fmt:message key="login.logout"/>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li><strong><a href="<c:url value='/login'/>">
                                    <fmt:message key="login.button.login"/>
                                </a></strong></li>
                                <li><strong><a href="<c:url value='/register'/>">
                                    <fmt:message key="registration.register"/>
                                </a></strong></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </header>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <footer class="footer">
            <div class="navbar">
                <div class="container-fluid" style="margin: 15px auto">
                    <div style="float: none; margin: 0 auto; color: #fff;
                                    font-weight: bold; font-size: 16px; text-align: center;">
                        © 2015 <fmt:message key="footer.assylkhanRakhatov"/> | ph 777-777 | <a href="#">FAQ's</a>

                    </div>
                </div>
            </div>
        </footer>
    </jsp:attribute>
    <jsp:body>
        <div class="content">
            <jsp:doBody/>
        </div>
        <div class="right-side-bar">
            <div role="tabpanel">
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active">
                        <a href="#news" aria-controls="news" role="tab"
                           data-toggle="tab"><span class="glyphicon glyphicon-globe"></span>
                            <fmt:message key="announcement.news"/> </a></li>
                    <li role="presentation">
                        <a href="#comments" aria-controls="comments" role="tab"
                           data-toggle="tab"><span class="glyphicon glyphicon-comment"></span>
                            <fmt:message key="comment.pageTitle"/></a></li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="news">
                        <c:forEach items="${announcements}" var="announcement">
                            <div>
                                <a href="#"><p>
                                    <fmt:message key="${announcement.body}"/></p></a>
                            </div>
                        </c:forEach>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="comments">
                        <c:forEach items="${comments}" var="comment">
                            <div>
                                <p>
                            <span style="color: #192236">@${comment.client.login} -
                            <fmt:formatDate value="${comment.leaveDate}" pattern="dd/MM/yyyy HH:mm"/></span>
                                        ${comment.body}</p>
                            </div>
                        </c:forEach>
                        <a href='<c:url value="/comments"/>'>show all</a>
                    </div>
                        <%--<div role="tabpanel" class="tab-pane" id="messages">asik</div>--%>
                        <%--<div role="tabpanel" class="tab-pane" id="settings">fergerger</div>--%>
                </div>
            </div>
        </div>
    </jsp:body>
</t:genericPage>
