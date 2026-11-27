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
            // Detect database type at runtime to support both H2 (tests) and SQL Server (production)
            def conn = dataSource.connection
            try {
                String productName = conn.metaData.databaseProductName?.toUpperCase()
                conn.close()
                if (productName?.contains('H2') == true) {
                    // H2: calculate stock directly from stock_transaction table
                    def row = sql.firstRow(
                        "SELECT COALESCE(SUM(CASE WHEN transaction_type = 'IN' THEN quantity ELSE -quantity END), 0) AS stock " +
                        "FROM stock_transaction WHERE product_id = ?",
                        [productId]
                    )
                    return row?.stock ?: 0
                }
            } catch (Exception e) {
                // If we can't determine the database, fall through to the dbo function
            }
            // SQL Server: use the dbo.GetCurrentStock function
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