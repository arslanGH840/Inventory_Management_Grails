package com.inventory

import grails.gorm.services.Service

@Service(StockTransaction)
interface StockTransactionService {

    StockTransaction get(Serializable id)

    List<StockTransaction> list(Map args)

    Long count()

    void delete(Serializable id)

    StockTransaction save(StockTransaction stockTransaction)

}