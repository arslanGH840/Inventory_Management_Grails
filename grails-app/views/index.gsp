<g:set var="productService" bean="productService"/>
<g:set var="supplierService" bean="supplierService"/>
<g:set var="stockTransactionService" bean="stockTransactionService"/>
<!doctype html>
<html>
<head>
    <title>Inventory App</title>
    <meta name="layout" content="main"/>
</head>

<body>
<div id="content" role="main">
    <h1>Inventory App</h1>
    <p class="text-muted">Simple inventory and restock management.</p>

    <ul class="summary-list" style="max-width: 28rem;">
        <li>
            <g:link controller="product" action="index" class="summary-item">
                <span class="summary-item-content">
                    <span>Products</span>
                    <span class="badge badge-secondary">${productService.count()}</span>
                </span>
            </g:link>
        </li>
        <li>
            <g:link controller="supplier" action="index" class="summary-item">
                <span class="summary-item-content">
                    <span>Suppliers</span>
                    <span class="badge badge-secondary">${supplierService.count()}</span>
                </span>
            </g:link>
        </li>
        <li>
            <g:link controller="stockTransaction" action="index" class="summary-item">
                <span class="summary-item-content">
                    <span>Stock transactions</span>
                    <span class="badge badge-secondary">${stockTransactionService.count()}</span>
                </span>
            </g:link>
        </li>
    </ul>

    <div class="actions">
        <g:link controller="product" action="create" class="btn btn-primary btn-sm">New product</g:link>
        <g:link controller="supplier" action="create" class="btn btn-outline-primary btn-sm">New supplier</g:link>
        <g:link controller="stockTransaction" action="create" class="btn btn-outline-primary btn-sm">Record transaction</g:link>
    </div>
</div>
</body>
</html>
