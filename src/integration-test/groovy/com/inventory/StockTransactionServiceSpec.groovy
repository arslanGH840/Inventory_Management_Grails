package com.inventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification

@Integration
@Rollback
class StockTransactionServiceSpec extends Specification {

    StockTransactionService stockTransactionService

    private Product newTestProduct() {
        new Product(
                name: "Txn Test Product", sku: "SKU-TXN-${UUID.randomUUID()}",
                unitPrice: 5.0, reorderThreshold: 2, active: true
        ).save(flush: true, failOnError: true)
    }

    private Supplier newTestSupplier() {
        new Supplier(
                name: "Txn Test Supplier", contactEmail: "txn@example.com", phone: "123", active: true
        ).save(flush: true, failOnError: true)
    }

    void "test get"() {
        expect:
        stockTransactionService.get(999999) == null
    }

    void "search filters by productId and type, and paginates"() {
        given:
        Product testProduct = newTestProduct()
        Supplier testSupplier = newTestSupplier()
        5.times {
            new StockTransaction(product: testProduct, supplier: testSupplier, quantity: 10, transactionType: 'IN', transactionDate: new Date())
                    .save(flush: true, failOnError: true)
        }
        3.times {
            new StockTransaction(product: testProduct, quantity: 2, transactionType: 'OUT', transactionDate: new Date())
                    .save(flush: true, failOnError: true)
        }

        expect:
        stockTransactionService.searchCount([productId: testProduct.id]) == 8
        stockTransactionService.searchCount([productId: testProduct.id, type: 'IN']) == 5
        stockTransactionService.search([productId: testProduct.id, type: 'OUT'], [:]).every { it.transactionType == 'OUT' }
        stockTransactionService.search([productId: testProduct.id], [max: 3]).size() == 3
    }
}