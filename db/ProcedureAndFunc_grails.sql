--A function to get current stock update form dabatase it minus out from in transaction in stock table

USE test;
GO

CREATE OR ALTER FUNCTION dbo.GetCurrentStock(@ProductId NUMERIC(19,0))
RETURNS INT
AS
BEGIN
    DECLARE @Stock INT;
    SELECT @Stock = ISNULL(SUM(CASE WHEN transaction_type = 'IN' THEN quantity ELSE -quantity END), 0)
    FROM stock_transaction
    WHERE product_id = @ProductId;
    RETURN ISNULL(@Stock, 0);
END;
GO

 --1. Inserts a new IN transaction into stock_transaction and
 --Updates the product's last_restocked_date
--stored procedure for the stock operatioons
CREATE OR ALTER PROCEDURE dbo.ProcessRestock
    @ProductId NUMERIC(19,0),
    @SupplierId NUMERIC(19,0),
    @Quantity INT
AS
BEGIN
    INSERT INTO stock_transaction (product_id, supplier_id, quantity, transaction_type, transaction_date, version)
    VALUES (@ProductId, @SupplierId, @Quantity, 'IN', GETDATE(), 0);

    UPDATE product SET last_restocked_date = GETDATE(), version = version + 1 WHERE id = @ProductId;
END;
GO