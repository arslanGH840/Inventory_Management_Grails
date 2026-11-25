package com.inventory.admin

import com.inventory.Product
import com.inventory.Supplier
import com.inventory.StockTransactionService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class StockTransactionController {

    StockTransactionService stockTransactionService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        Map filters = [
                productId : params.productId as Long,
                supplierId: params.supplierId as Long,
                type      : params.type,
                fromDate  : params.fromDate ? Date.parse('yyyy-MM-dd', params.fromDate) : null,
                toDate    : params.toDate ? Date.parse('yyyy-MM-dd', params.toDate) : null
        ]
        respond stockTransactionService.search(filters, params), model: [
                stockTransactionCount: stockTransactionService.searchCount(filters),
                products             : Product.findAllByActive(true, [sort: 'name']),
                suppliers            : Supplier.findAllByActive(true, [sort: 'name'])
        ]
    }

    def show(Long id) {
        respond stockTransactionService.get(id)
    }
}