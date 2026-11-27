﻿<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<g:set var="stockService" bean="stockService"/>
<div id="content" role="main">
    <div class="page-header">
        <h1><g:message code="default.list.label" args="[entityName]"/></h1>
        <g:link class="btn btn-primary btn-sm" action="create">
            <g:message code="default.new.label" args="[entityName]"/>
        </g:link>
    </div>

    <table class="admin-table">
        <thead>
        <tr>
            <th>Name</th>
            <th>SKU</th>
            <th><g:message code="product.unitPrice" default="Price"/></th>
            <th><g:message code="stock.currentStock" default="Current Stock"/></th>
            <th><g:message code="default.action.label" default="Action"/></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${productList}" var="p">
            <tr>
                <td><g:link action="show" id="${p.id}">${p.name}</g:link></td>
                <td>${p.sku}</td>
                <td>${p.unitPrice}</td>
                <td>
                    <g:set var="currentStock" value="${stockService.getCurrentStock(p.id)}"/>
                    <g:if test="${currentStock > 0}">
                        <g:render template="/layouts/statusBadge" model="[status: 'instock']"/> ${currentStock}
                    </g:if>
                    <g:else>
                        <g:render template="/layouts/statusBadge" model="[status: 'outofstock']"/>
                    </g:else>
                </td>
                <td class="actions actions-tight">
                    <g:link class="btn btn-outline-secondary btn-sm" action="edit" id="${p.id}">
                        <g:message code="default.button.edit.label" default="Edit"/>
                    </g:link>
                    <g:link class="btn btn-outline-primary btn-sm" action="restockForm" id="${p.id}">Restock</g:link>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <g:if test="${productCount > params.int('max')}">
        <div class="pagination-bar" role="navigation" aria-label="Pagination">
            <g:paginate activeClass="active" class="btn" total="${productCount ?: 0}"/>
        </div>
    </g:if>
</div>
</body>
</html>