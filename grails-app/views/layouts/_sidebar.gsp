<aside class="admin-sidebar">
    <div class="admin-sidebar-brand">
        <g:link controller="dashboard" action="index"><g:message code="app.name" default="Inventory App"/></g:link>
    </div>
    <nav class="admin-sidebar-nav">
        <g:link class="admin-nav-link ${controllerName == 'dashboard' ? 'active' : ''}" controller="dashboard" action="index">
            <g:message code="nav.dashboard" default="Dashboard"/>
        </g:link>
        <g:link class="admin-nav-link ${controllerName == 'product' ? 'active' : ''}" controller="product" action="index">
            <g:message code="nav.products" default="Products"/>
        </g:link>
        <g:link class="admin-nav-link ${controllerName == 'supplier' ? 'active' : ''}" controller="supplier" action="index">
            <g:message code="nav.suppliers" default="Suppliers"/>
        </g:link>
        <g:link class="admin-nav-link ${controllerName == 'withdrawalRequest' ? 'active' : ''}" controller="withdrawalRequest" action="index">
            <g:message code="nav.withdrawalRequests" default="Withdrawal Requests"/>
        </g:link>
        <g:link class="admin-nav-link ${controllerName == 'stockTransaction' ? 'active' : ''}" controller="stockTransaction" action="index">
            <g:message code="nav.transactionHistory" default="Transaction History"/>
        </g:link>
    </nav>
</aside>