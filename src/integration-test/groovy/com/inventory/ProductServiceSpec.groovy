package com.inventory

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification

@Integration
@Rollback
class ProductServiceSpec extends Specification {

    ProductService productService

    private Product newValidProduct(String suffix) {
        new Product(
                name: "Test Product $suffix",
                sku: "SKU-$suffix",
                unitPrice: 9.99,
                reorderThreshold: 5,
                active: true
        )
    }

    void "test get"() {
        expect:
        productService.get(1) == null
    }

    void "test list"() {
        setup:
        20.times { newValidProduct(it.toString()).save(flush: true, failOnError: true) }

        expect:
        productService.list().size() == 20
        productService.list(max: 10).size() == 10
    }

    void "test count"() {
        setup:
        20.times { newValidProduct(it.toString()).save(flush: true, failOnError: true) }

        expect:
        productService.count() == 20
    }

    void "test delete"() {
        setup:
        Product product = newValidProduct("del").save(flush: true, failOnError: true)
        Long id = product.id

        expect:
        productService.count() == 1

        when:
        productService.delete(id)
        Product.withSession { it.flush() }

        then:
        Product.count() == 1
        !Product.get(id).active
    }

    void "test save"() {
        expect:
        productService.save(newValidProduct("save")) != null
    }
}