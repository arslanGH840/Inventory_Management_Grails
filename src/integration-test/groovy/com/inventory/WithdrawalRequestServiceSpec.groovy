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
}