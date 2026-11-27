package com.inventory.user

import com.inventory.Product
import com.inventory.StockService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
class StockController {

    StockService stockService

    def index() {
        List<Product> products = Product.findAllByActive(true, [sort: 'name'])
        [items: products.collect { Product p -> [product: p, currentStock: stockService.getCurrentStock(p.id)] }]
    }
}