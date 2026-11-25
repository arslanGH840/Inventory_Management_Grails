package com.inventory

import groovy.sql.Sql
import javax.sql.DataSource

class StockService {

    DataSource dataSource

    Integer getCurrentStock(Long productId) {
        def sql = new Sql(dataSource)
        try {
            def row = sql.firstRow("SELECT dbo.GetCurrentStock(?) AS stock", [productId])
            return row?.stock ?: 0
        } finally {
            sql.close()
        }
    }

    void processRestock(Long productId, Long supplierId, Integer quantity) {
        def sql = new Sql(dataSource)
        try {
            sql.call("{call dbo.ProcessRestock(?, ?, ?)}", [productId, supplierId, quantity])
        } finally {
            sql.close()
        }
    }
}