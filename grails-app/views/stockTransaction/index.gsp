<!doctype html>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <title><g:message code="nav.transactionHistory" default="Transaction History"/></title>
</head>
<body>
<g:form controller="stockTransaction" action="index" method="GET" class="filter-form">
    <div class="filter-field">
        <label class="filter-label" for="productId"><g:message code="product.label" default="Product"/></label>
        <g:select class="form-select" id="productId" name="productId" from="${products}" optionKey="id" optionValue="name" noSelection="['':'All products']" value="${params.productId}"/>
    </div>
    <div class="filter-field">
        <label class="filter-label" for="supplierId"><g:message code="supplier.label" default="Supplier"/></label>
        <g:select class="form-select" id="supplierId" name="supplierId" from="${suppliers}" optionKey="id" optionValue="name" noSelection="['':'All suppliers']" value="${params.supplierId}"/>
    </div>
    <div class="filter-field">
        <label class="filter-label" for="type"><g:message code="stockTransaction.transactionType" default="Type"/></label>
        <g:select class="form-select" id="type" name="type" from="${['IN', 'OUT']}" noSelection="['':'All types']" value="${params.type}"/>
    </div>
    <div class="filter-field">
        <label class="filter-label" for="fromDate">From</label>
        <g:field class="form-control" type="date" id="fromDate" name="fromDate" value="${params.fromDate}"/>
    </div>
    <div class="filter-field">
        <label class="filter-label" for="toDate">To</label>
        <g:field class="form-control" type="date" id="toDate" name="toDate" value="${params.toDate}"/>
    </div>
    <div class="filter-field filter-field-submit">
        <button class="btn btn-primary btn-sm" type="submit"><g:message code="action.filter" default="Filter"/></button>
    </div>
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