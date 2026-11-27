<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <g:set var="entityName" value="${message(code: 'supplier.label', default: 'Supplier')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div id="content" role="main">
    <div class="page-header">
        <h1><g:message code="default.create.label" args="[entityName]"/></h1>
        <g:link class="btn btn-outline-secondary btn-sm" action="index">Back to list</g:link>
    </div>

    <g:hasErrors bean="${this.supplier}">
        <ul class="alert alert-error list-unstyled" role="alert">
            <g:eachError bean="${this.supplier}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                    <g:message error="${error}"/>
                </li>
            </g:eachError>
        </ul>
    </g:hasErrors>

    <g:form resource="${this.supplier}" controller="${controllerName}" method="POST">
        <fieldset>
            <f:all bean="supplier" class="form-row"
                   requiredClass="required"
                   labelClass="col-label form-label"
                   divClass="col-value"
                   widget-class="form-control"
                   widget-invalidClass="is-invalid"
                   widget-selectDateClass="form-select"
                   widget-checkBoxClass="form-check-input align-middle"/>
        </fieldset>
        <div class="actions">
            <button class="btn btn-primary" type="submit">
                ${message(code: 'default.button.create.label', default: 'Create')}
            </button>
            <g:link class="btn btn-outline-secondary" action="index">Cancel</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
