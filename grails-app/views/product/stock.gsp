<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <title>Stock Level</title>
</head>

<body>
<div id="content" role="main">
    <div class="page-header">
        <h1>Stock level</h1>
        <div class="actions actions-tight">
            <g:link class="btn btn-primary btn-sm" controller="product" action="restockForm" id="${product?.id}">
                Restock
            </g:link>
            <g:link class="btn btn-outline-secondary btn-sm" controller="product" action="show" id="${product?.id}">
                Back to product
            </g:link>
        </div>
    </div>

    <div class="panel" style="max-width: 32rem;">
        <div class="panel-body">
            <h2>${product?.name}</h2>
            <table class="data-table data-table-sm">
                <tr>
                    <th>SKU</th>
                    <td>${product?.sku}</td>
                </tr>
                <tr>
                    <th>Unit price</th>
                    <td>${product?.unitPrice}</td>
                </tr>
                <tr>
                    <th>Current stock</th>
                    <td><span class="badge badge-primary">${currentStock}</span></td>
                </tr>
                <tr>
                    <th>Last restocked</th>
                    <td>${product?.lastRestockedDate ?: 'Never'}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>
