<!doctype html>
<html>
<head>
    <meta name="layout" content="userLayout"/>
    <title><g:message code="nav.browseStock" default="Browse Stock"/></title>
</head>
<body>
<table class="admin-table">
    <thead>
    <tr>
        <th><g:message code="product.label" default="Product"/></th>
        <th><g:message code="product.unitPrice" default="Price"/></th>
        <th><g:message code="stock.currentStock" default="Current Stock"/></th>
        <th><g:message code="default.action.label" default="Action"/></th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${items}" var="item">
        <tr>
            <td>${item.product.name}</td>
            <td>${item.product.unitPrice}</td>
            <td>
                <g:if test="${item.currentStock > 0}">
                    <g:render template="/layouts/statusBadge" model="[status: 'instock']"/> ${item.currentStock}
                </g:if>
                <g:else>
                    <g:render template="/layouts/statusBadge" model="[status: 'outofstock']"/>
                </g:else>
            </td>
            <td>
                <g:link controller="myRequest" action="create" params="[productId: item.product.id]" class="btn btn-sm btn-primary">
                    <g:message code="action.requestWithdrawal" default="Request"/>
                </g:link>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>
</body>
</html>