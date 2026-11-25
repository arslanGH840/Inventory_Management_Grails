package com.inventory

class StockTransaction {
    Product product
    Supplier supplier
    WithdrawalRequest sourceRequest
    Integer quantity
    String transactionType  // 'IN' or 'OUT'
    Date transactionDate

    static constraints = {
        product nullable: false
        supplier nullable: true
        sourceRequest nullable: true
        quantity min: 1
        transactionType inList: ['IN', 'OUT']
        transactionDate nullable: false
    }

    static mapping = {
        table 'stock_transaction'
    }
}