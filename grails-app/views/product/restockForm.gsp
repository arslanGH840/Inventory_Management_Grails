<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <title>Restock Product</title>
</head>

<body>
<div id="content" role="main">
    <h1>Restock: ${product?.name}</h1>

    <g:if test="${!suppliers}">
        <div class="alert alert-warning" style="max-width: 32rem;">
            No suppliers available.
            <g:link controller="supplier" action="create">Add a supplier</g:link> first.
        </div>
        <g:link class="btn btn-outline-secondary" controller="product" action="viewStock" id="${product?.id}">
            Back to stock
        </g:link>
    </g:if>
    <g:else>
        <g:form controller="product" action="doRestock" class="panel" style="max-width: 32rem;">
            <div class="panel-body">
                <g:hiddenField name="productId" value="${product?.id}"/>

                <div class="form-row">
                    <label class="form-label" for="supplierId">Supplier</label>
                    <g:select class="form-select" name="supplierId" from="${suppliers}"
                              optionKey="id" optionValue="name"/>
                </div>

                <div class="form-row">
                    <label class="form-label" for="quantity">Quantity</label>
                    <g:field class="form-control" type="number" name="quantity" value="1" min="1" required="required"/>
                </div>

                <div class="actions">
                    <button class="btn btn-primary" type="submit">Restock</button>
                    <g:link class="btn btn-outline-secondary" controller="product" action="viewStock" id="${product?.id}">
                        Cancel
                    </g:link>
                </div>
            </div>
        </g:form>
    </g:else>
</div>
</body>
</html>
