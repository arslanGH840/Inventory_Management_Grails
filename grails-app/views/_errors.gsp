<%@ page import="org.springframework.validation.Errors" %>
<g:if test="${flash.message}">
    <div class="alert alert-success">${flash.message}</div>
</g:if>
<g:if test="${errors}">
    <div class="alert alert-danger">
        <ul>
            <g:eachError bean="${errors}" var="error">
                <li><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </div>
</g:if>