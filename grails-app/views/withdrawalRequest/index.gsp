<!doctype html>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <title><g:message code="nav.withdrawalRequests" default="Withdrawal Requests"/></title>
</head>
<body>
<table class="admin-table">
    <thead>
    <tr>
        <th><g:message code="product.label" default="Product"/></th>
        <th><g:message code="withdrawalRequest.requestedBy" default="Requested By"/></th>
        <th><g:message code="withdrawalRequest.quantity" default="Quantity"/></th>
        <th><g:message code="withdrawalRequest.requestDate" default="Requested"/></th>
        <th><g:message code="withdrawalRequest.status" default="Status"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${withdrawalRequestList}" var="wr">
        <tr>
            <td>${wr.product?.name}</td>
            <td>${wr.requestedBy?.username}</td>
            <td>${wr.quantity}</td>
            <td><g:formatDate date="${wr.requestDate}" format="yyyy-MM-dd HH:mm"/></td>
            <td><g:render template="/layouts/statusBadge" model="[status: wr.status]"/></td>
            <td>
                <g:form controller="withdrawalRequest" action="decide" method="POST" class="inline-form">
                    <g:hiddenField name="requestId" value="${wr.id}"/>
                    <input type="text" name="rejectionReason" class="form-control form-control-sm"
                           placeholder="${message(code: 'withdrawalRequest.rejectionReason', default: 'Reason (required to reject)')}"/>
                    <button class="btn btn-sm btn-success" name="decision" value="APPROVE" type="submit">
                        <g:message code="action.approve" default="Approve"/>
                    </button>
                    <button class="btn btn-sm btn-danger" name="decision" value="REJECT" type="submit">
                        <g:message code="action.reject" default="Reject"/>
                    </button>
                </g:form>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>
<g:paginate total="${withdrawalRequestCount ?: 0}"/>
</body>
</html>