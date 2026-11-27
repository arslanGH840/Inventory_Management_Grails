package com.inventory

import com.inventory.command.WithdrawalDecisionCommand
import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification

@Integration
@Rollback
class WithdrawalRequestServiceSpec extends Specification {

    WithdrawalRequestService withdrawalRequestService
    StockService stockService

    private Product productWithStock(int stock) {
        Product product = new Product(name: 'Widget', sku: "SKU-${UUID.randomUUID()}", unitPrice: 1.0, reorderThreshold: 1, active: true)
                .save(flush: true, failOnError: true)
        new StockTransaction(product: product, quantity: stock, transactionType: 'IN', transactionDate: new Date())
                .save(flush: true, failOnError: true)
        product
    }

    private User aUser(String prefix) {
        new User(username: "${prefix}-${UUID.randomUUID()}", password: 'pass').save(flush: true, failOnError: true)
    }

    void "approving a request creates an OUT transaction and reduces available stock"() {
        given:
        Product product = productWithStock(10)
        WithdrawalRequest request = new WithdrawalRequest(product: product, requestedBy: aUser('user'), quantity: 6, status: 'PENDING')
                .save(flush: true, failOnError: true)

        when:
        Map result = withdrawalRequestService.approve(new WithdrawalDecisionCommand(requestId: request.id, decision: 'APPROVE'), aUser('admin'))

        then:
        result.success
        stockService.getCurrentStock(product.id) == 4
    }

    void "a second pending request that would exceed the now-reduced stock fails cleanly with stock.insufficient"() {
        given: "a product with only enough stock for one of two 7-unit requests"
        Product product = productWithStock(10)
        User requester = aUser('user')
        WithdrawalRequest firstRequest  = new WithdrawalRequest(product: product, requestedBy: requester, quantity: 7, status: 'PENDING').save(flush: true, failOnError: true)
        WithdrawalRequest secondRequest = new WithdrawalRequest(product: product, requestedBy: requester, quantity: 7, status: 'PENDING').save(flush: true, failOnError: true)

        when: "the first request is approved"
        Map firstResult = withdrawalRequestService.approve(new WithdrawalDecisionCommand(requestId: firstRequest.id, decision: 'APPROVE'), aUser('admin'))

        then:
        firstResult.success
        stockService.getCurrentStock(product.id) == 3

        when: "the second is approved against the now-reduced stock"
        Map secondResult = withdrawalRequestService.approve(new WithdrawalDecisionCommand(requestId: secondRequest.id, decision: 'APPROVE'), aUser('admin'))

        then: "it is refused rather than overdrawing stock, and stays PENDING"
        !secondResult.success
        secondResult.code == 'stock.insufficient'
        WithdrawalRequest.get(secondRequest.id).status == 'PENDING'
    }

    void "approving an already-decided request is refused with withdrawal.already.decided"() {
        given:
        Product product = productWithStock(10)
        WithdrawalRequest request = new WithdrawalRequest(
                product: product, requestedBy: aUser('user'), quantity: 2, status: 'APPROVED', decisionDate: new Date()
        ).save(flush: true, failOnError: true)

        when:
        Map result = withdrawalRequestService.approve(new WithdrawalDecisionCommand(requestId: request.id, decision: 'APPROVE'), aUser('admin'))

        then:
        !result.success
        result.code == 'withdrawal.already.decided'
    }
    void "a rejected request can be resubmitted exactly once, and a second rejection blocks further resubmission"() {
        given:
        Product product = productWithStock(10)
        User user = aUser('user')
        WithdrawalRequest original = new WithdrawalRequest(product: product, requestedBy: user, quantity: 3, status: 'PENDING')
                .save(flush: true, failOnError: true)

        when: "the original request is rejected"
        Map rejectResult = withdrawalRequestService.decide(
                new WithdrawalDecisionCommand(requestId: original.id, decision: 'REJECT', rejectionReason: 'Not needed'),
                aUser('admin')
        )

        then:
        rejectResult.success
        WithdrawalRequest.get(original.id).status == 'REJECTED'

        when: "the user resubmits it"
        Map resubmitResult = withdrawalRequestService.resubmit(original.id, user)

        then:
        resubmitResult.success
        WithdrawalRequest resubmission = resubmitResult.request
        resubmission.resubmittedFrom.id == original.id
        resubmission.status == 'PENDING'

        when: "the user tries to resubmit the SAME original request again"
        Map secondAttempt = withdrawalRequestService.resubmit(original.id, user)

        then: "refused — one resubmission per rejected request"
        !secondAttempt.success
        secondAttempt.code == 'withdrawal.resubmit.alreadyResubmitted'

        when: "the resubmission is ALSO rejected"
        Map secondRejectResult = withdrawalRequestService.decide(
                new WithdrawalDecisionCommand(requestId: resubmission.id, decision: 'REJECT', rejectionReason: 'Still not needed'),
                aUser('admin')
        )

        then:
        secondRejectResult.success

        when: "the user tries to resubmit the second-generation rejected request"
        Map blocked = withdrawalRequestService.resubmit(resubmission.id, user)

        then: "blocked — must contact an admin directly"
        !blocked.success
        blocked.code == 'withdrawal.resubmit.notAllowed'
    }
}