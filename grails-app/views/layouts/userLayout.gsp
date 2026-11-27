<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title><g:layoutTitle default="${message(code: 'app.name', default: 'Inventory App')}"/></title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
</head>
<body class="admin-body">
<div class="admin-shell">
    <g:render template="/layouts/userSidebar"/>
    <div class="admin-main">
        <g:render template="/layouts/topbar"/>
        <main class="admin-content" role="main">
            <g:render template="/errors"/>
            <g:layoutBody/>
        </main>
    </div>
</div>
</body>
</html>