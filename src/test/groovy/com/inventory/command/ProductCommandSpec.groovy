package com.inventory.command

import com.inventory.Product
import grails.testing.gorm.DataTest
import spock.lang.Specification

class ProductCommandSpec extends Specification implements DataTest {

    def setupSpec() {
        mockDomains(Product)
    }

    void "fails when name is blank"() {
        given:
        def cmd = new ProductCommand(name: '', sku: 'SKU-1', unitPrice: 1.0, reorderThreshold: 1)

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('name')
    }

    void "fails with product.sku.duplicate when sku belongs to another product"() {
        given:
        new Product(name: 'Existing', sku: 'SKU-DUP', unitPrice: 1.0, reorderThreshold: 1, active: true)
                .save(flush: true, failOnError: true)
        def cmd = new ProductCommand(name: 'New', sku: 'SKU-DUP', unitPrice: 1.0, reorderThreshold: 1)

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('sku').code == 'product.sku.duplicate'
    }

    void "allows a product to keep its own sku on update"() {
        given:
        Product existing = new Product(name: 'Existing', sku: 'SKU-SELF', unitPrice: 1.0, reorderThreshold: 1, active: true)
                .save(flush: true, failOnError: true)
        def cmd = new ProductCommand(id: existing.id, name: 'Existing', sku: 'SKU-SELF', unitPrice: 1.0, reorderThreshold: 1)

        expect:
        cmd.validate()
    }

    void "fails with product.unitPrice.invalid when price is negative"() {
        given:
        def cmd = new ProductCommand(name: 'X', sku: 'SKU-NEG', unitPrice: -1.0, reorderThreshold: 1)

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('unitPrice').code == 'product.unitPrice.invalid'
    }
}