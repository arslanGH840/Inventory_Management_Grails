package com.inventory.command

import com.inventory.Product
import com.inventory.Supplier
import grails.testing.gorm.DataTest
import spock.lang.Specification

class RestockCommandSpec extends Specification implements DataTest {

    def setupSpec() {
        mockDomains(Product, Supplier)
    }

    void "fails with restock.product.notfound when product doesn't exist"() {
        given:
        def cmd = new RestockCommand(productId: 999L, supplierId: 1L, quantity: 5)

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('productId').code == 'restock.product.notfound'
    }

    void "fails with restock.supplier.notfound when supplier doesn't exist"() {
        given:
        Product product = new Product(name: 'P', sku: 'SKU-R1', unitPrice: 1.0, reorderThreshold: 1, active: true).save(flush: true)
        def cmd = new RestockCommand(productId: product.id, supplierId: 999L, quantity: 5)

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('supplierId').code == 'restock.supplier.notfound'
    }

    void "fails with restock.quantity.invalid when quantity is zero or negative"() {
        given:
        Product product = new Product(name: 'P2', sku: 'SKU-R2', unitPrice: 1.0, reorderThreshold: 1, active: true).save(flush: true)
        Supplier supplier = new Supplier(name: 'S', contactEmail: 'a@b.com', phone: '123', active: true).save(flush: true)
        def cmd = new RestockCommand(productId: product.id, supplierId: supplier.id, quantity: 0)

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('quantity').code == 'restock.quantity.invalid'
    }
}