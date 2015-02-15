<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.messages"/>
<fmt:message var="commentTitle" key="comment.pageTitle"/>
<t:clientPage title="${commentTitle}">

    <div class="comments">
        <h2 align=center><fmt:message key="comment.message.leaveComment"/></h2><br/><br/>
        <c:forEach items="${allComments}" var="comment">
            <div class="row comment">
                <div class="col-md-3" style="margin: auto;">
                    <p>${comment.client.login}</p>

                    <p><fmt:formatDate value="${comment.leaveDate}" pattern="dd/MM/yyyy HH:mm"/></p>
                </div>
                <div class="col-md-6" style="margin: auto;">
                    <p>${comment.body}</p>
                </div>
            </div>
        </c:forEach>
    </div>
</t:clientPage>
