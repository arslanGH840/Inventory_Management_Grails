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
        <a class="site-brand" href="${createLink(uri: '/')}">Inventory App</a>
        <button class="nav-toggle" type="button" aria-controls="mainNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="nav-toggle-icon"></span>
        </button>
        <div class="nav-collapse" id="mainNav">
            <ul class="site-nav">
                <li class="site-nav-item">
                    <g:link class="site-nav-link ${!controllerName ? 'active' : ''}" uri="/">Home</g:link>
                </li>
                <li class="site-nav-item">
                    <g:link class="site-nav-link ${controllerName == 'product' ? 'active' : ''}"
                            controller="product" action="index">Products</g:link>
                </li>
                <li class="site-nav-item">
                    <g:link class="site-nav-link ${controllerName == 'supplier' ? 'active' : ''}"
                            controller="supplier" action="index">Suppliers</g:link>
                </li>
                <li class="site-nav-item">
                    <g:link class="site-nav-link ${controllerName == 'stockTransaction' ? 'active' : ''}"
                            controller="stockTransaction" action="index">Transactions</g:link>
                </li>
            </ul>
        </div>
    </div>
</nav>

<main class="container site-main" style="margin-left: 33%">
    <g:flashMessages/>
    <g:layoutBody/>
</main>

<footer class="site-footer">
    <div class="container site-footer-text">Inventory App</div>
</footer>

<div id="spinner" class="spinner" style="display:none;">
    <span class="visually-hidden">Loading...</span>
</div>
<asset:javascript src="application.js"/>
<script>
  (() => {
    const toggler = document.querySelector('.nav-toggle');
    const collapse = document.getElementById('mainNav');
    if (!toggler || !collapse) return;
    toggler.addEventListener('click', () => {
      collapse.classList.toggle('show');
    });
    collapse.querySelectorAll('.site-nav-link').forEach(link => {
      link.addEventListener('click', () => {
        collapse.classList.remove('show');
      });
    });
  })();
</script>
</body>
</html>
