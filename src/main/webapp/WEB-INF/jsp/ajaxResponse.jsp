<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:clientPage title="taxi info">
  <dl>
    <dt>car image</dt>
    <dd><img src="/static/image/CaddyCTS_V.jpg" alt="caddillac"/></dd>

    <dt>car gov number</dt>
    <dd>${driver.govNumber}</dd>

    <dt>car model</dt>
    <dd>${driver.carModel}</dd>

    <dt>car type</dt>
    <dd>${driver.carClass}</dd>
    <br/>
    <h3>see notifications page to check if arrived the taxi</h3>
  </dl>
</t:clientPage>