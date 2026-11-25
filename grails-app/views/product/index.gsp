<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div id="content" role="main">
    <div class="page-header">
        <h1><g:message code="default.list.label" args="[entityName]"/></h1>
        <g:link class="btn btn-primary btn-sm" action="create">
            <g:message code="default.new.label" args="[entityName]"/>
        </g:link>
    </div>

    <f:table class="data-table data-table-striped data-table-sm" controller="${controllerName}" collection="${productList}"/>

    <g:if test="${productCount > params.int('max')}">
        <div class="pagination-bar" role="navigation" aria-label="Pagination">
            <g:paginate activeClass="active" class="btn" total="${productCount ?: 0}"/>
        </div>
    </g:if>
</div>
</body>
</html>
