<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.messages"/>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:driverPage title="driver login">
  <form class="alert alert-info col-lg-3" style="margin: 70px auto; float: none" action='<c:url value="/driverLogin"/>' method="post">
    <c:if test="${not empty loginError}">
      <strong class="alert-danger">
        <fmt:message key="${loginError}"/>
      </strong><br/></c:if>
    <br/>
    <t:input type="text" placeholder="login" value="${param.login}" name="login"/>
    <t:input type="password" placeholder="password" value="${param.password}" name="password"/>
    <t:input className="btn btn-info" value="login" type="submit"/>
  </form>
</t:driverPage>
