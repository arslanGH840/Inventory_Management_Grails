<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div id="content" role="main">
    <div class="page-header">
        <h1><g:message code="default.show.label" args="[entityName]"/></h1>
        <g:link class="btn btn-outline-secondary btn-sm" action="index">Back to list</g:link>
    </div>

    <f:display bean="product" listClass="container" listItemClass="row form-row"
               labelClass="col-label form-label" valueClass="col-value"/>

    <div class="actions">
        <g:link class="btn btn-primary" action="edit" resource="${this.product}" controller="${controllerName}">
            <g:message code="default.button.edit.label" default="Edit"/>
        </g:link>
        <g:link class="btn btn-outline-primary" controller="product" action="viewStock" id="${product?.id}">
            View stock
        </g:link>
        <g:link class="btn btn-outline-primary" controller="product" action="restockForm" id="${product?.id}">
            Restock
        </g:link>
        <g:form resource="${this.product}" controller="${controllerName}" method="DELETE" class="inline-form">
            <button class="btn btn-outline-danger" type="submit"
                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                ${message(code: 'default.button.delete.label', default: 'Delete')}
            </button>
        </g:form>
    </div>
</div>
</body>
</html>
