package com.inventory.admin

import com.inventory.WithdrawalRequestService
import com.inventory.command.WithdrawalDecisionCommand
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class WithdrawalRequestController {

    WithdrawalRequestService withdrawalRequestService
    SpringSecurityService springSecurityService

    static allowedMethods = [decide: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond withdrawalRequestService.pending(params),
                model: [withdrawalRequestCount: withdrawalRequestService.pendingCount()]
    }

    def decide(WithdrawalDecisionCommand cmd) {
        if (cmd.hasErrors()) {
            flash.message = message(code: cmd.errors.allErrors[0].defaultMessage ?: 'decision.reason.required')
            redirect action: "index"
            return
        }

        def admin = springSecurityService.currentUser
        def result = withdrawalRequestService.decide(cmd, admin)

        flash.message = result.success ?
                message(code: 'withdrawal.decision.recorded') :
                message(code: result.code)

        redirect action: "index"
    }
}