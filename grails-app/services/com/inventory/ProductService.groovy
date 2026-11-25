package com.inventory

import grails.gorm.transactions.Transactional

@Transactional
class ProductService {

    StockService stockService

    Product get(Serializable id) {
        Product.get(id)
    }

    List<Product> list(Map params = [:]) {
        Product.createCriteria().list(max: params.max, offset: params.offset ?: 0) {
            order('name', 'asc')
        }
    }

    Long count() {
        Product.count()
    }

    Product save(Product product) {
        product.save(failOnError: true)
        product
    }

    // Soft delete only — history in stock_transaction / withdrawal_request
    // must survive a product being retired, and inactive products are
    // hidden from user-side browsing and new requests (Technical PRD §2.6.3).
    void delete(Serializable id) {
        Product product = Product.get(id)
        if (product) {
            product.active = false
            product.save(flush: true, failOnError: true)
        }
    }

    // Active products whose live (derived) stock has fallen below their
    // reorder threshold. Current stock is computed in SQL Server
    // (dbo.GetCurrentStock), not a GORM column, so the active-only filter
    // runs as a Criteria and the threshold check runs per-product against
    // the live figure from StockService.
    List<Map> lowStock() {
        List<Product> activeProducts = Product.createCriteria().list {
            eq('active', true)
            order('name', 'asc')
        }
        activeProducts.collect { Product p -> [product: p, currentStock: stockService.getCurrentStock(p.id)] }
                .findAll { it.currentStock < it.product.reorderThreshold }
    }
}