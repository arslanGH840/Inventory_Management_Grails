<header class="admin-topbar">
    <h1 class="admin-page-title"><g:layoutTitle default=""/></h1>
    <div class="admin-topbar-user">
        <sec:ifLoggedIn>
            <span class="admin-username"><sec:username/></span>
            <g:link controller="logout"><g:message code="nav.logout" default="Logout"/></g:link>
        </sec:ifLoggedIn>
    </div>
</header>