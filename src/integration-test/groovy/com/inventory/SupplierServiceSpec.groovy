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