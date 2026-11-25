package com.inventory.support

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp

/**
 * H2-only stand-ins for the SQL Server dbo.GetCurrentStock function and
 * dbo.ProcessRestock stored procedure, so the integration test suite
 * (which runs on H2) exercises the same raw-JDBC code path in
 * StockService as production (SQL Server) does.
 */
class H2StockFunctions {

    static int getCurrentStock(Connection conn, long productId) {
        conn.prepareStatement(
                "SELECT COALESCE(SUM(CASE WHEN transaction_type = 'IN' THEN quantity ELSE -quantity END), 0) AS stock " +
                        "FROM stock_transaction WHERE product_id = ?"
        ).withCloseable { PreparedStatement ps ->
            ps.setLong(1, productId)
            ps.executeQuery().withCloseable { ResultSet rs ->
                rs.next()
                return rs.getInt('stock')
            }
        }
    }

    static void processRestock(Connection conn, long productId, long supplierId, int quantity) {
        conn.prepareStatement(
                "INSERT INTO stock_transaction (product_id, supplier_id, quantity, transaction_type, transaction_date, version) " +
                        "VALUES (?, ?, ?, 'IN', ?, 0)"
        ).withCloseable { PreparedStatement ps ->
            ps.setLong(1, productId)
            ps.setLong(2, supplierId)
            ps.setInt(3, quantity)
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()))
            ps.executeUpdate()
        }
        conn.prepareStatement(
                "UPDATE product SET last_restocked_date = ?, version = version + 1 WHERE id = ?"
        ).withCloseable { PreparedStatement ps ->
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()))
            ps.setLong(2, productId)
            ps.executeUpdate()
        }
    }
}