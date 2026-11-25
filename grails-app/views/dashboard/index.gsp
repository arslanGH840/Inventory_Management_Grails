<!doctype html>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <title><g:message code="dashboard.title" default="Dashboard"/></title>
</head>
<body>
<div class="stat-card-row">
    <g:render template="/layouts/statCard" model="[value: totalProducts, label: message(code: 'dashboard.totalProducts', default: 'Total Products'), tone: 'primary']"/>
    <g:render template="/layouts/statCard" model="[value: lowStockCount, label: message(code: 'dashboard.lowStockCount', default: 'Low Stock Items'), tone: 'warning']"/>
    <g:render template="/layouts/statCard" model="[value: pendingRequests, label: message(code: 'dashboard.pendingRequests', default: 'Pending Requests'), tone: 'accent']"/>
    <g:render template="/layouts/statCard" model="[value: weekTransactions, label: message(code: 'dashboard.weekTransactions', default: \"This Week's Transactions\"), tone: 'danger']"/>
</div>
</body>
</html>