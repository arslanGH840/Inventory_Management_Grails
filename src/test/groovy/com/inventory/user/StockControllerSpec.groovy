package com.inventory.user

import com.inventory.Product
import com.inventory.StockService
import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class StockControllerSpec extends Specification implements ControllerUnitTest<StockController>, DomainUnitTest<Product> {

    void "index lists only active products with their current stock"() {
        given:
        Product active = new Product(name: 'Active Product', sku: 'SKU-ACTIVE', unitPrice: 5.0, reorderThreshold: 2, active: true)
                .save(flush: true, failOnError: true)
        new Product(name: 'Inactive Product', sku: 'SKU-INACTIVE', unitPrice: 5.0, reorderThreshold: 2, active: false)
                .save(flush: true, failOnError: true)

        controller.stockService = Mock(StockService) {
            getCurrentStock(active.id) >> 7
        }

        when:
        def model = controller.index()

        then:
        model.items.size() == 1
        model.items[0].product.id == active.id
        model.items[0].currentStock == 7
    }
}