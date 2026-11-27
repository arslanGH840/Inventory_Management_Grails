<!doctype html>
<html>
<head>
    <title>Inventory App</title>
    <meta name="layout" content="landingLayout"/>
</head>

<body>
<div id="content" role="main">

    <section class="hero">
        <div class="hero-inner">
            <span class="hero-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5Z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg>
            </span>
            <h1>Inventory App</h1>
            <p class="hero-subtitle">
                A simple, reliable way to track products, suppliers, and stock movements in one place.
            </p>
            <g:link class="btn btn-primary" uri="/login/auth">
                <span>Login to continue</span>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round" style="width:1rem;height:1rem;"><path d="M13.5 4.5L21 12m0 0l-7.5 7.5M21 12H3"/></svg>
            </g:link>
        </div>
    </section>

    <section class="features">
        <div class="feature-card feature-card-primary">
            <span class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5Z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg>
            </span>
            <h3>Product catalog</h3>
            <p>Keep a single source of truth for every product you stock, with current quantities always up to date.</p>
        </div>
        <div class="feature-card feature-card-accent">
            <span class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><rect x="1" y="6" width="13" height="10" rx="1.5"/><path d="M14 10h4l3 3v3h-7z"/><circle cx="6" cy="18" r="1.8"/><circle cx="17.5" cy="18" r="1.8"/></svg>
            </span>
            <h3>Supplier management</h3>
            <p>Track who supplies what, so restocking is a quick lookup instead of a guessing game.</p>
        </div>
        <div class="feature-card feature-card-warning">
            <span class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3.5 2"/></svg>
            </span>
            <h3>Stock transactions</h3>
            <p>Every restock, withdrawal, and adjustment is logged, so you always know how inventory levels changed and why.</p>
        </div>
        <div class="feature-card feature-card-danger">
            <span class="feature-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="9" cy="8" r="3"/><path d="M3 20c0-3 2.5-5 6-5s6 2 6 5"/><circle cx="17" cy="9" r="2.3"/><path d="M15.5 13.2c2.4.4 4 1.9 4.5 3.8"/></svg>
            </span>
            <h3>Role-based access</h3>
            <p>Admins manage the catalog and approve requests; staff can submit withdrawal requests through their own view.</p>
        </div>
    </section>

</div>
</body>
</html>