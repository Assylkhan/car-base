<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:driverPage title="driver profile">
  <strong style="color: darkred">${changeResult}</strong><br/><br/>
  <p><strong><a href='<c:url value="/changeDriverState"/>'>change state</a></strong></p>
  <dl>
    <dt>first name</dt>
    <dd>${driver.firstName}</dd>

    <dt>last name</dt>
    <dd>${driver.lastName}</dd>

    <dt>currentLocation</dt>
    <dd>${driver.currentLocation}</dd>

    <dt>available</dt>
    <dd>${driver.available}</dd>

    <dt>login</dt>
    <dd>${driver.login}</dd>

  </dl>

</t:driverPage>
