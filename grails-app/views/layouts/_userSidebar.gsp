<aside class="admin-sidebar">
    <g:link class="admin-sidebar-brand" controller="stock" action="index">
        <span class="brand-dot">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5Z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg>
        </span>
        <g:message code="app.name" default="Inventory App"/>
    </g:link>

    <div class="admin-sidebar-group">
        <div class="admin-sidebar-group-label"><g:message code="nav.group.menu" default="Menu"/></div>
        <nav class="admin-sidebar-nav">
            <g:link class="admin-nav-link ${controllerName == 'stock' ? 'active' : ''}" controller="stock" action="index">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M12 3 2 8l10 5 10-5-10-5Z"/><path d="M2 13l10 5 10-5"/></svg>
                <span><g:message code="nav.browseStock" default="Browse Stock"/></span>
            </g:link>
            <g:link class="admin-nav-link ${controllerName == 'myRequest' ? 'active' : ''}" controller="myRequest" action="index">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M7 3h7l4 4v14H7z"/><path d="M14 3v4h4"/><path d="M9.5 13h5M9.5 16.5h5"/></svg>
                <span><g:message code="nav.myRequests" default="My Requests"/></span>
            </g:link>
        </nav>
    </div>
</aside>