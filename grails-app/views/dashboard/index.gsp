<!doctype html>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <title><g:message code="dashboard.title" default="Dashboard"/></title>
</head>
<body>

<g:set var="icnProducts"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5Z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg></g:set>
<g:set var="icnWarning"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M12 3 2 20h20L12 3Z"/><path d="M12 9v5"/><path d="M12 17h.01"/></svg></g:set>
<g:set var="icnInbox"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M4 12h4l1.5 3h5L16 12h4"/><path d="M4 12l1.2-6.2A1.5 1.5 0 0 1 6.67 4.5h10.66a1.5 1.5 0 0 1 1.47 1.3L20 12"/><path d="M4 12v5.5A1.5 1.5 0 0 0 5.5 19h13a1.5 1.5 0 0 0 1.5-1.5V12"/></svg></g:set>
<g:set var="icnClock"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3.5 2"/></svg></g:set>

<div class="stat-card-row">
    <g:render template="/layouts/statCard" model="[value: totalProducts, label: message(code: 'dashboard.totalProducts', default: 'Total Products'), tone: 'primary', icon: icnProducts]"/>
    <g:render template="/layouts/statCard" model="[value: lowStockCount, label: message(code: 'dashboard.lowStockCount', default: 'Low Stock Items'), tone: 'warning', icon: icnWarning]"/>
    <g:render template="/layouts/statCard" model="[value: pendingRequests, label: message(code: 'dashboard.pendingRequests', default: 'Pending Requests'), tone: 'accent', icon: icnInbox]"/>
    <g:render template="/layouts/statCard" model="[value: weekTransactions, label: message(code: 'dashboard.weekTransactions', default: 'This Weeks Transactions'), tone: 'danger', icon: icnClock]"/>
</div>
</body>
</html>