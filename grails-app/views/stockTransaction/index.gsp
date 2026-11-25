<!doctype html>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <title><g:message code="nav.transactionHistory" default="Transaction History"/></title>
</head>
<body>
<g:form controller="stockTransaction" action="index" method="GET" class="filter-form">
    <g:select name="productId" from="${products}" optionKey="id" optionValue="name" noSelection="['':'All products']" value="${params.productId}"/>
    <g:select name="supplierId" from="${suppliers}" optionKey="id" optionValue="name" noSelection="['':'All suppliers']" value="${params.supplierId}"/>
    <g:select name="type" from="${['IN', 'OUT']}" noSelection="['':'All types']" value="${params.type}"/>
    <g:field type="date" name="fromDate" value="${params.fromDate}"/>
    <g:field type="date" name="toDate" value="${params.toDate}"/>
    <button class="btn btn-primary btn-sm" type="submit"><g:message code="action.filter" default="Filter"/></button>
</g:form>

<table class="admin-table">
    <thead>
    <tr>
        <th><g:message code="product.label" default="Product"/></th>
        <th><g:message code="supplier.label" default="Supplier"/></th>
        <th><g:message code="stockTransaction.transactionType" default="Type"/></th>
        <th><g:message code="stockTransaction.quantity" default="Quantity"/></th>
        <th><g:message code="stockTransaction.transactionDate" default="Date"/></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${stockTransactionList}" var="tx">
        <tr>
            <td>${tx.product?.name}</td>
            <td>${tx.supplier?.name ?: '-'}</td>
            <td><g:render template="/layouts/statusBadge" model="[status: tx.transactionType]"/></td>
            <td>${tx.quantity}</td>
            <td><g:formatDate date="${tx.transactionDate}" format="yyyy-MM-dd HH:mm"/></td>
        </tr>
    </g:each>
    </tbody>
</table>
<g:paginate total="${stockTransactionCount ?: 0}" params="${params}"/>
</body>
</html>