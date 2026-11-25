package com.inventory

import com.inventory.command.RestockCommand
import grails.gorm.transactions.Transactional
import groovy.sql.Sql

import javax.sql.DataSource

class StockService {

    DataSource dataSource

    Integer getCurrentStock(Long productId) {
        def sql = new Sql(dataSource)
        try {
            def row = sql.firstRow("SELECT dbo.GetCurrentStock(?) AS stock", [productId])
            return row?.stock ?: 0
        } catch (Exception e) {
            throw new BusinessException('stock.lookup.failed', e)
        } finally {
            sql.close()
        }
    }

    @Transactional
    Map processRestock(RestockCommand cmd) {
        def sql = new Sql(dataSource)
        try {
            sql.call("{call dbo.ProcessRestock(?, ?, ?)}", [cmd.productId, cmd.supplierId, cmd.quantity])
            return [success: true]
        } catch (Exception e) {
            throw new BusinessException('restock.procedure.failed', e)
        } finally {
            sql.close()
        }
    }
}