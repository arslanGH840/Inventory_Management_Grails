package com.inventory.security

import grails.plugin.springsecurity.web.authentication.AjaxAwareAuthenticationSuccessHandler
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication

/**
 * Sends each user to the portal that matches their role after a successful login.
 */
class RoleBasedAuthenticationSuccessHandler extends AjaxAwareAuthenticationSuccessHandler {

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        def roles = authentication?.authorities?.collect { it.authority } ?: []
        if (roles.contains('ROLE_ADMIN')) {
            return '/dashboard/index'
        }
        if (roles.contains('ROLE_USER')) {
            return '/stock/index'
        }
        super.determineTargetUrl(request, response, authentication)
    }
}
