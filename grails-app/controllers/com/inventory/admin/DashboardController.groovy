package com.inventory.admin

import com.inventory.Product
import com.inventory.ProductService
import com.inventory.StockTransaction
import com.inventory.WithdrawalRequestService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class DashboardController {

    ProductService productService
    WithdrawalRequestService withdrawalRequestService

    def index() {
        Date weekAgo = new Date() - 7
        [
                totalProducts   : Product.countByActive(true),
                lowStockCount   : productService.lowStock().size(),
                pendingRequests : withdrawalRequestService.pendingCount(),
                weekTransactions: StockTransaction.countByTransactionDateGreaterThanEquals(weekAgo)
        ]
    }
}