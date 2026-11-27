<!doctype html>
<html>
<head>
    <meta name="layout" content="userLayout"/>
    <title><g:message code="nav.myRequests" default="My Requests"/></title>
</head>
<body>
<g:link controller="myRequest" action="create" class="btn btn-primary btn-sm" style="margin-bottom: 1rem;">
    <g:message code="action.requestWithdrawal" default="New Request"/>
</g:link>
<table class="admin-table">
    <thead>
    <tr>
        <th><g:message code="product.label" default="Product"/></th>
        <th><g:message code="withdrawalRequest.quantity" default="Quantity"/></th>
        <th><g:message code="withdrawalRequest.requestDate" default="Requested"/></th>
        <th><g:message code="withdrawalRequest.status" default="Status"/></th>
        <th><g:message code="withdrawalRequest.rejectionReason" default="Rejection Reason"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${requests}" var="wr">
        <tr>
            <td>${wr.product?.name}</td>
            <td>${wr.quantity}</td>
            <td><g:formatDate date="${wr.requestDate}" format="yyyy-MM-dd HH:mm"/></td>
            <td><g:render template="/layouts/statusBadge" model="[status: wr.status]"/></td>
            <td>${wr.status == 'REJECTED' ? wr.rejectionReason : '-'}</td>
            <td>
                <g:if test="${wr.status == 'REJECTED' && wr.resubmittedFrom == null}">
                    <g:form controller="myRequest" action="resubmit" method="POST" class="inline-form">
                        <g:hiddenField name="id" value="${wr.id}"/>
                        <button class="btn btn-sm btn-outline-primary" type="submit">
                            <g:message code="action.resubmit" default="Resubmit"/>
                        </button>
                    </g:form>
                </g:if>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>
</body>
</html>