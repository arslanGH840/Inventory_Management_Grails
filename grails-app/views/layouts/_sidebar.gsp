<aside class="admin-sidebar">
    <g:link class="admin-sidebar-brand" controller="dashboard" action="index">
        <span class="brand-dot">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5Z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg>
        </span>
        <g:message code="app.name" default="Inventory App"/>
    </g:link>

    <div class="admin-sidebar-group">
        <div class="admin-sidebar-group-label"><g:message code="nav.group.overview" default="Overview"/></div>
        <nav class="admin-sidebar-nav">
            <g:link class="admin-nav-link ${controllerName == 'dashboard' ? 'active' : ''}" controller="dashboard" action="index">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="7" height="7" rx="1.5"/><rect x="14" y="3" width="7" height="7" rx="1.5"/><rect x="3" y="14" width="7" height="7" rx="1.5"/><rect x="14" y="14" width="7" height="7" rx="1.5"/></svg>
                <span><g:message code="nav.dashboard" default="Dashboard"/></span>
            </g:link>
        </nav>
    </div>

    <div class="admin-sidebar-group">
        <div class="admin-sidebar-group-label"><g:message code="nav.group.inventory" default="Inventory"/></div>
        <nav class="admin-sidebar-nav">
            <g:link class="admin-nav-link ${controllerName == 'product' ? 'active' : ''}" controller="product" action="index">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5Z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg>
                <span><g:message code="nav.products" default="Products"/></span>
            </g:link>
            <g:link class="admin-nav-link ${controllerName == 'supplier' ? 'active' : ''}" controller="supplier" action="index">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><rect x="1" y="6" width="13" height="10" rx="1.5"/><path d="M14 10h4l3 3v3h-7z"/><circle cx="6" cy="18" r="1.8"/><circle cx="17.5" cy="18" r="1.8"/></svg>
                <span><g:message code="nav.suppliers" default="Suppliers"/></span>
            </g:link>
            <g:link class="admin-nav-link ${controllerName == 'stockTransaction' ? 'active' : ''}" controller="stockTransaction" action="index">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3.5 2"/></svg>
                <span><g:message code="nav.transactionHistory" default="Transaction History"/></span>
            </g:link>
        </nav>
    </div>

    <div class="admin-sidebar-group">
        <div class="admin-sidebar-group-label"><g:message code="nav.group.requests" default="Requests"/></div>
        <nav class="admin-sidebar-nav">
            <g:link class="admin-nav-link ${controllerName == 'withdrawalRequest' ? 'active' : ''}" controller="withdrawalRequest" action="index">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M4 12h4l1.5 3h5L16 12h4"/><path d="M4 12l1.2-6.2A1.5 1.5 0 0 1 6.67 4.5h10.66a1.5 1.5 0 0 1 1.47 1.3L20 12"/><path d="M4 12v5.5A1.5 1.5 0 0 0 5.5 19h13a1.5 1.5 0 0 0 1.5-1.5V12"/></svg>
                <span><g:message code="nav.withdrawalRequests" default="Withdrawal Requests"/></span>
            </g:link>
        </nav>
    </div>
</aside>