<!doctype html>
<html>
<head>
    <meta name="layout" content="loginLayout"/>
    <title><g:message code="login.title" default="Sign In"/></title>
</head>
<body>
<div class="login-card">
    <h1 class="login-brand">
        <span class="brand-dot">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 8l-9-5-9 5 9 5 9-5Z"/><path d="M3 8v8l9 5 9-5V8"/><path d="M12 13v8"/></svg>
        </span>
        <g:message code="app.name" default="Inventory App"/>
    </h1>

    <g:if test="${params.login_error}">
        <div class="alert alert-danger"><g:message code="login.error" default="Invalid username or password."/></div>
    </g:if>

    <form action="${postUrl ?: '/login/authenticate'}" method="POST" id="loginForm" class="login-form" novalidate="novalidate">
        <div class="form-row">
            <label class="form-label" for="username"><g:message code="login.username" default="Username"/></label>
            <input class="form-control" type="text" name="${usernameParameter ?: 'username'}" id="username" autocomplete="username" required="required"/>
            <div class="invalid-feedback" id="username-feedback"><g:message code="login.username.blank" default="Please enter your username."/></div>
        </div>
        <div class="form-row">
            <label class="form-label" for="password"><g:message code="login.password" default="Password"/></label>
            <input class="form-control" type="password" name="${passwordParameter ?: 'password'}" id="password" autocomplete="current-password" required="required"
                   data-msg-blank="${message(code: 'login.password.blank', default: 'Please enter your password.')}"
                   data-msg-short="${message(code: 'login.password.short', default: 'Password must be at least 4 characters.')}"/>
            <div class="invalid-feedback" id="password-feedback"></div>
        </div>
        <button class="btn btn-primary login-submit" type="submit"><g:message code="login.submit" default="Sign In"/></button>
    </form>
</div>

<script>
    (function () {
        var form = document.getElementById('loginForm');
        var username = document.getElementById('username');
        var password = document.getElementById('password');
        var MIN_PASSWORD_LENGTH = 4;

        function setValid(field) {
            field.classList.remove('is-invalid');
        }

        function setInvalid(field) {
            field.classList.add('is-invalid');
        }

        function validateUsername() {
            username.value = username.value.trim();
            var ok = username.value.length > 0;
            ok ? setValid(username) : setInvalid(username);
            return ok;
        }

        function validatePassword() {
            var feedback = document.getElementById('password-feedback');
            if (password.value.length === 0) {
                feedback.textContent = password.getAttribute('data-msg-blank');
                setInvalid(password);
                return false;
            }
            if (password.value.length < MIN_PASSWORD_LENGTH) {
                feedback.textContent = password.getAttribute('data-msg-short');
                setInvalid(password);
                return false;
            }
            setValid(password);
            return true;
        }

        username.addEventListener('blur', validateUsername);
        password.addEventListener('blur', validatePassword);
        username.addEventListener('input', function () { setValid(username); });
        password.addEventListener('input', function () { setValid(password); });

        form.addEventListener('submit', function (e) {
            var usernameOk = validateUsername();
            var passwordOk = validatePassword();
            if (!usernameOk || !passwordOk) {
                e.preventDefault();
                (usernameOk ? password : username).focus();
            }
        });
    })();
</script>
</body>
</html>