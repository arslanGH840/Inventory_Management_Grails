package com.inventory

import grails.gorm.transactions.Transactional

@Transactional
class SupplierService {

    Supplier get(Serializable id) {
        Supplier.get(id)
    }

    List<Supplier> list(Map params = [:]) {
        Supplier.createCriteria().list(max: params.max, offset: params.offset ?: 0) {
            order('name', 'asc')
        }
    }

    Long count() {
        Supplier.count()
    }

    Supplier save(Supplier supplier) {
        supplier.save(failOnError: true)
        supplier
    }

    // Suppliers are never force-deleted while referenced by stock history;
    // the delete is refused with a message-bundle code (Technical PRD §2.6.2).
    void delete(Serializable id) {
        Supplier supplier = Supplier.get(id)
        if (!supplier) return

        Long referenceCount = StockTransaction.createCriteria().count {
            eq('supplier', supplier)
        }
        if (referenceCount > 0) {
            throw new BusinessException('supplier.delete.hasTransactions')
        }
        supplier.delete(flush: true)
    }
}