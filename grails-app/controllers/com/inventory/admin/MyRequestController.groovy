package com.inventory.user

import com.inventory.Product
import com.inventory.User
import com.inventory.WithdrawalRequestService
import com.inventory.command.WithdrawalRequestCommand
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
class MyRequestController {

    WithdrawalRequestService withdrawalRequestService
    SpringSecurityService springSecurityService

    static allowedMethods = [submit: "POST", resubmit: "POST"]

    def index() {
        User user = springSecurityService.currentUser as User
        [requests: withdrawalRequestService.myRequests(user)]
    }

    def create() {
        WithdrawalRequestCommand cmd = new WithdrawalRequestCommand(productId: params.long('productId'))
        [command: cmd, products: Product.findAllByActive(true, [sort: 'name'])]
    }

    def submit(WithdrawalRequestCommand cmd) {
        if (cmd.hasErrors()) {
            flash.message = message(code: cmd.errors.allErrors[0].defaultMessage ?: 'withdrawal.quantity.invalid')
            redirect action: "create", params: [productId: cmd.productId]
            return
        }
        User user = springSecurityService.currentUser as User
        withdrawalRequestService.submit(cmd, user)
        flash.message = message(code: 'withdrawal.request.submitted')
        redirect action: "index"
    }

    def resubmit(Long id) {
        User user = springSecurityService.currentUser as User
        Map result = withdrawalRequestService.resubmit(id, user)
        flash.message = result.success ?
                message(code: 'withdrawal.resubmit.success') :
                message(code: result.code)
        redirect action: "index"
    }
}