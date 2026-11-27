<!doctype html>
<html>
<head>
    <meta name="layout" content="userLayout"/>
    <title><g:message code="myRequest.newTitle" default="Request Withdrawal"/></title>
</head>
<body>
<g:form controller="myRequest" action="submit" method="POST" class="panel" style="max-width: 32rem;">
    <div class="form-row">
        <label class="form-label" for="productId"><g:message code="product.label" default="Product"/></label>
        <g:select class="form-select" name="productId" from="${products}" optionKey="id" optionValue="name" value="${command?.productId}"/>
    </div>
    <div class="form-row">
        <label class="form-label" for="quantity"><g:message code="withdrawalRequest.quantity" default="Quantity"/></label>
        <g:field class="form-control" type="number" name="quantity" min="1" value="${command?.quantity ?: 1}" required="required"/>
    </div>
    <div class="actions">
        <button class="btn btn-primary" type="submit"><g:message code="action.submit" default="Submit Request"/></button>
        <g:link class="btn btn-outline-secondary" controller="myRequest" action="index"><g:message code="action.cancel" default="Cancel"/></g:link>
    </div>
</g:form>
</body>
</html>