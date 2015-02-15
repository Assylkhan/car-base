<%@tag description="Input With Error Field" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="name" %>
<%@attribute name="type" %>
<%@attribute name="placeholder" %>
<%@attribute name="fieldError" %>
<%@attribute name="className" %>
<%@attribute name="value" %>
<%@attribute name="label" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="i18n.messages"/>
<div class="row">
    <div class="form-group col-lg-12">
        <c:if test="${not empty label}">
            <label>${label}</label>
        </c:if>
        <input class='<c:out value="${className}" default="form-control"/>' type="${type}" name="${name}"
               value="${value}" placeholder="${placeholder}"/>
        <c:if test="${not empty fieldError}">
            <small class="text-danger"><fmt:message key="${fieldError}"/></small>
        </c:if>
    </div>
</div>