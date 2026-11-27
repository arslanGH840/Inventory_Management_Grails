<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="adminLayout"/>
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div id="content" role="main">
    <div class="page-header">
        <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
        <g:link class="btn btn-outline-secondary btn-sm" action="index">Back to list</g:link>
    </div>

    <g:hasErrors bean="${this.product}">
        <ul class="alert alert-error list-unstyled" role="alert">
            <g:eachError bean="${this.product}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                    <g:message error="${error}"/>
                </li>
            </g:eachError>
        </ul>
    </g:hasErrors>

    <g:form resource="${this.product}" controller="${controllerName}" method="PUT">
        <g:hiddenField name="version" value="${this.product?.version}"/>
        <fieldset>
            <f:all bean="product" class="form-row"
                   except="lastRestockedDate"
                   requiredClass="required"
                   labelClass="col-label form-label"
                   divClass="col-value"
                   widget-class="form-control"
                   widget-invalidClass="is-invalid"
                   widget-selectDateClass="form-select"
                   widget-checkBoxClass="form-check-input align-middle"/>

            <div class="form-row">
                <label class="col-label form-label" for="lastRestockedDate">
                    <g:message code="product.lastRestockedDate.label" default="Last Restocked Date"/>
                </label>
                <div class="col-value">
                    <g:field type="date" class="form-control" id="lastRestockedDate" name="lastRestockedDate"
                             value="${product?.lastRestockedDate ? product.lastRestockedDate.format('yyyy-MM-dd') : ''}"/>
                </div>
            </div>
        </fieldset>
        <div class="actions">
            <button class="btn btn-primary" type="submit">
                ${message(code: 'default.button.update.label', default: 'Update')}
            </button>
            <g:link class="btn btn-outline-secondary" action="show" id="${this.product?.id}">Cancel</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
