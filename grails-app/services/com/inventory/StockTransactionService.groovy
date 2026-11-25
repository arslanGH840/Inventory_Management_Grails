package com.inventory

import grails.gorm.transactions.Transactional
import org.hibernate.criterion.CriteriaSpecification

@Transactional
class StockTransactionService {

    StockTransaction get(Serializable id) {
        StockTransaction.get(id)
    }

    // Filterable, paginated transaction history. `filters` may carry
    // productId, supplierId, type ('IN'/'OUT'), fromDate, toDate — any
    // combination applies together (Technical PRD §2.4).
    List<StockTransaction> search(Map filters = [:], Map pagination = [:]) {
        StockTransaction.createCriteria().list(max: pagination.max, offset: pagination.offset ?: 0) {
            createAlias('product', 'p')
            createAlias('supplier', 's', CriteriaSpecification.LEFT_JOIN)
            if (filters.productId) eq('p.id', filters.productId as Long)
            if (filters.supplierId) eq('s.id', filters.supplierId as Long)
            if (filters.type) eq('transactionType', filters.type)
            if (filters.fromDate) ge('transactionDate', filters.fromDate)
            if (filters.toDate) le('transactionDate', filters.toDate)
            order('transactionDate', 'desc')
        }
    }

    Long searchCount(Map filters = [:]) {
        StockTransaction.createCriteria().count {
            createAlias('product', 'p')
            createAlias('supplier', 's', CriteriaSpecification.LEFT_JOIN)
            if (filters.productId) eq('p.id', filters.productId as Long)
            if (filters.supplierId) eq('s.id', filters.supplierId as Long)
            if (filters.type) eq('transactionType', filters.type)
            if (filters.fromDate) ge('transactionDate', filters.fromDate)
            if (filters.toDate) le('transactionDate', filters.toDate)
        }
    }
}