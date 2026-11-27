<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title><g:layoutTitle default="Inventory App"/></title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
</head>

<body class="site-body">

<nav class="site-navbar">
    <div class="container">
        <a class="site-brand" href="${createLink(uri: '/')}">
            <span class="brand-dot">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5Z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg>
            </span>
            Inventory App
        </a>
        <div class="nav-collapse" style="display:block;">
            <ul class="site-nav">
                <li class="site-nav-item">
                    <g:link class="btn btn-primary btn-sm" uri="/login/auth">Login</g:link>
                </li>
            </ul>
        </div>
    </div>
</nav>

<main class="site-main">
    <g:flashMessages/>
    <g:layoutBody/>
</main>

<footer class="site-footer">
    <div class="container site-footer-text">Inventory App</div>
</footer>

<asset:javascript src="application.js"/>
</body>
</html>