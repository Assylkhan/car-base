<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:dispatcherPage title="dispatcher profile">

    <dl>
        <dt>first name</dt>
        <dd>${dispatcher.firstName}</dd>

        <dt>last name</dt>
        <dd>${dispatcher.lastName}</dd>

        <dt>login</dt>
        <dd>${dispatcher.login}</dd>

        <dd>national id</dd>
        <dt>${dispatcher.nationalId}</dt>

    </dl>

</t:dispatcherPage>
