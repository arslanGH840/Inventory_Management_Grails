<header class="admin-topbar">
    <h1 class="admin-page-title"><g:layoutTitle default=""/></h1>
    <div class="admin-topbar-user">
        <sec:ifLoggedIn>
            <g:set var="uname"><sec:username/></g:set>
            <div class="admin-user-chip">
                <span class="admin-user-avatar">${uname?.take(1)?.toUpperCase()}</span>
                <span class="admin-username">${uname}</span>
            </div>
            <g:link class="logout-link" uri="/logout"><g:message code="nav.logout" default="Logout"/></g:link>
        </sec:ifLoggedIn>
    </div>
</header>