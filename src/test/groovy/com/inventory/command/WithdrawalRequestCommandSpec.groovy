package com.inventory.command

import com.inventory.Product
import grails.testing.gorm.DataTest
import spock.lang.Specification

class WithdrawalRequestCommandSpec extends Specification implements DataTest {

    def setupSpec() {
        mockDomains(Product)
    }

    void "fails with withdrawal.product.notfound for an inactive (soft-deleted) product"() {
        given:
        Product inactive = new Product(name: 'Gone', sku: 'SKU-INACTIVE', unitPrice: 1.0, reorderThreshold: 1, active: false)
                .save(flush: true, failOnError: true)
        def cmd = new WithdrawalRequestCommand(productId: inactive.id, quantity: 2)

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('productId').code == 'withdrawal.product.notfound'
    }

    void "fails with withdrawal.quantity.invalid when quantity is not positive"() {
        given:
        Product product = new Product(name: 'P', sku: 'SKU-OK', unitPrice: 1.0, reorderThreshold: 1, active: true)
                .save(flush: true, failOnError: true)
        def cmd = new WithdrawalRequestCommand(productId: product.id, quantity: -1)

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('quantity').code == 'withdrawal.quantity.invalid'
    }
}