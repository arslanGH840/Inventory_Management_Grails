package com.inventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification

@Integration
@Rollback
class SupplierServiceSpec extends Specification {

    SupplierService supplierService

    private Supplier newValidSupplier(String suffix) {
        new Supplier(
                name: "Test Supplier $suffix",
                contactEmail: "supplier$suffix@example.com",
                phone: "1234567890",
                active: true
        )
    }

    void "test get"() {
        expect:
        supplierService.get(1) == null
    }

    void "test list"() {
        setup:
        20.times { newValidSupplier(it.toString()).save(flush: true, failOnError: true) }

        expect:
        supplierService.list().size() == 20
        supplierService.list(max: 10).size() == 10
    }

    void "test count"() {
        setup:
        20.times { newValidSupplier(it.toString()).save(flush: true, failOnError: true) }

        expect:
        supplierService.count() == 20
    }

    void "test delete"() {
        setup:
        Supplier supplier = newValidSupplier("del").save(flush: true, failOnError: true)
        Long id = supplier.id

        expect:
        supplierService.count() == 1

        when:
        supplierService.delete(id)
        Supplier.withSession { it.flush() }

        then:
        Supplier.count() == 0
    }

    void "test save"() {
        expect:
        supplierService.save(newValidSupplier("save")) != null
    }
}
void "delete is blocked with supplier.delete.hasTransactions when referenced by a stock transaction"() {
    given:
    Product product = new Product(name: 'Ref Product', sku: "SKU-${UUID.randomUUID()}", unitPrice: 1.0, reorderThreshold: 1, active: true)
            .save(flush: true, failOnError: true)
    Supplier supplier = new Supplier(name: 'Ref Supplier', contactEmail: 'ref@example.com', phone: '123', active: true)
            .save(flush: true, failOnError: true)
    new StockTransaction(product: product, supplier: supplier, quantity: 5, transactionType: 'IN', transactionDate: new Date())
            .save(flush: true, failOnError: true)

    when:
    supplierService.delete(supplier.id)

    then:
    BusinessException e = thrown(BusinessException)
    e.code == 'supplier.delete.hasTransactions'
    Supplier.get(supplier.id) != null
}