package com.inventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification

@Integration
@Rollback
class StockTransactionServiceSpec extends Specification {

    StockTransactionService stockTransactionService

    private Product testProduct

    def setup() {
        testProduct = new Product(
                name: "Txn Test Product",
                sku: "SKU-TXN-${System.currentTimeMillis()}",
                unitPrice: 5.0,
                reorderThreshold: 2,
                active: true
        ).save(flush: true, failOnError: true)
    }

    private StockTransaction newValidTransaction() {
        new StockTransaction(
                product: testProduct,
                quantity: 10,
                transactionType: 'IN',
                transactionDate: new Date()
        )
    }

    void "test get"() {
        expect:
        stockTransactionService.get(1) == null
    }

    void "test list"() {
        setup:
        20.times { newValidTransaction().save(flush: true, failOnError: true) }

        expect:
        stockTransactionService.list().size() == 20
        stockTransactionService.list(max: 10).size() == 10
    }

    void "test count"() {
        setup:
        20.times { newValidTransaction().save(flush: true, failOnError: true) }

        expect:
        stockTransactionService.count() == 20
    }

    void "test delete"() {
        setup:
        StockTransaction txn = newValidTransaction().save(flush: true, failOnError: true)
        Long id = txn.id

        expect:
        stockTransactionService.count() == 1

        when:
        stockTransactionService.delete(id)
        StockTransaction.withSession { it.flush() }

        then:
        StockTransaction.count() == 0
    }

    void "test save"() {
        expect:
        stockTransactionService.save(newValidTransaction()) != null
    }
}