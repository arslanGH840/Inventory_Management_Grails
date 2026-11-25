# Inventory Management System

A role-based Inventory Management System built with **Grails (Groovy)**, **GORM/Hibernate 5**, **Spring Security Core**, and **Microsoft SQL Server** (with **H2** for testing). It separates administrative and user workflows across dedicated portals, enforces stock safety inside database transactions, and routes all form inputs and error paths through command objects and `messages.properties`[cite: 1, 2, 3, 7].

---

## Architecture & Key Design Decisions

- **Two Portals, One Codebase:** Routes are split across `/admin/**` (`com.inventory.admin.*Controller`, `@Secured(['ROLE_ADMIN'])`) and `/app/**` (`com.inventory.user.*Controller`, `@Secured(['ROLE_USER', 'ROLE_ADMIN'])`) with distinct layouts (`adminLayout.gsp`, `userLayout.gsp`) and a standalone `loginLayout.gsp`[cite: 1, 2, 8].
- **Ask-and-Approve Withdrawal Workflow:** Regular users never mutate stock directly[cite: 1]. Users submit `WithdrawalRequest` records (`PENDING`, `APPROVED`, `REJECTED`), and only an Admin approval creates an `OUT` `StockTransaction` linked via `sourceRequest`[cite: 2, 7]. Rejected requests support a single resubmission (`resubmittedFrom`) before requiring direct Admin follow-up on a second rejection[cite: 7, 14].
- **Concurrency-Safe Approval:** Approving a withdrawal request acquires a pessimistic row lock (`WithdrawalRequest.lock(...)`) inside a `@Transactional` boundary, re-checks live stock at decision time, creates the `OUT` `StockTransaction`, and updates request status (`decidedBy`, `decisionDate`) atomically[cite: 2, 6, 7, 16].
- **Database-Backed Stock & Restocks:**
  - `dbo.GetCurrentStock(@ProductId)` (SQL Server scalar function) computes live stock (`IN` minus `OUT`) from `stock_transaction` and is queried by `StockService.getCurrentStock(productId)` via raw JDBC (`groovy.sql.Sql`)[cite: 2, 4, 13, 14].
  - `dbo.ProcessRestock(@ProductId, @SupplierId, @Quantity)` (SQL Server stored procedure) records an `IN` `StockTransaction` and updates `product.last_restocked_date`[cite: 4, 13, 14].
- **Command-Class Validation & i18n:** All writes bind through Command classes (`RestockCommand`, `WithdrawalRequestCommand`, `WithdrawalDecisionCommand`, `ProductCommand`, `SupplierCommand`) rather than direct domain binding[cite: 3, 4, 15]. Failures map to message codes in `grails-app/i18n/messages.properties` and render consistently via `_errors.gsp` and `<g:message code="..."/>`[cite: 7, 8, 10].
- **Safe Deletion Rules:** Products use soft deletion (`active = false`) to preserve historical records and hide inactive items from user browsing/requests[cite: 1, 7]. Suppliers check `StockTransaction` references before deletion and block removal with `supplier.delete.hasTransactions` when referenced[cite: 2, 4, 7, 15].

---

## Roles & Features

### `ROLE_ADMIN` (`/admin/**`)
- **Dashboard:** Summary stat cards for total products, low-stock count, pending withdrawal requests, and this-week transactions[cite: 9].
- **Products:** Create, edit, and soft-delete products (`name`, unique `sku`, `unitPrice`, `reorderThreshold`, `lastRestockedDate`, `active`), plus view low-stock items below `reorderThreshold`[cite: 1, 4, 9, 10].
- **Suppliers:** Create, edit, and delete suppliers (`name`, `contactEmail`, `phone`, `active`) with reference checks blocking deletion when referenced by stock transactions[cite: 2, 4, 9, 11].
- **Restock:** Record incoming stock against an active product and supplier, triggering `dbo.ProcessRestock`[cite: 4, 9, 13].
- **Withdrawal Requests:** Review pending requests, approve to deduct stock, or reject with a mandatory `rejectionReason`[cite: 2, 3, 9].
- **Transaction History:** Filter and paginate `StockTransaction` history by product, supplier (left join), transaction type (`IN`/`OUT`), and date range[cite: 2, 4, 6, 9].

### `ROLE_USER` (`/app/**`)
- **Browse Stock:** View active products with current stock levels and clear out-of-stock indicators[cite: 7, 9].
- **Request Withdrawal:** Submit requests for active products and valid positive quantities via `WithdrawalRequestCommand`[cite: 3, 9].
- **My Requests:** Track personal request statuses (`PENDING`, `APPROVED`, `REJECTED`), view rejection reasons, and resubmit an eligible rejected request once[cite: 2, 7, 9, 16].

---

## Database Schema Overview

Core tables managed via GORM/Hibernate alongside SQL Server objects[cite: 1, 10, 13]:
- `product` (`id`, `name`, `sku` unique, `unit_price`, `reorder_threshold`, `last_restocked_date`, `active`)[cite: 10]
- `supplier` (`id`, `name`, `contact_email`, `phone`, `active`)[cite: 10, 11]
- `stock_transaction` (`id`, `product_id`, `supplier_id` nullable, `quantity`, `transaction_type` `IN`/`OUT`, `transaction_date`, `source_request_id` nullable)[cite: 11]
- `withdrawal_request` (`id`, `product_id`, `requested_by_id`, `quantity`, `status` `PENDING`/`APPROVED`/`REJECTED`, `request_date`, `decision_date`, `decided_by_id` nullable, `rejection_reason` nullable, plus resubmission tracking)[cite: 7, 12, 15]
- `user`, `role`, `user_role` (scaffolded via Spring Security Core `s2-quickstart com.inventory User Role`)[cite: 2, 12]

Indexes & DB objects[cite: 12, 13]:
- Unique index on `product.sku`[cite: 12]
- Indexes on `stock_transaction(product_id, transaction_date)`, `withdrawal_request(status)`, and `withdrawal_request(requested_by_id)`[cite: 12, 13]
- SQL Server function `dbo.GetCurrentStock` and stored procedure `dbo.ProcessRestock` (stored in `db/functions_and_procedures.sql`, not generated by GORM)[cite: 13, 14]

---

## Local Setup & Running

1. **Prepare SQL Server (Dev/Prod):**
   - Create the target SQL Server database and configure your Grails datasource connection[cite: 1].
   - Execute your SQL Server function and stored procedure script (e.g., `db/functions_and_procedures.sql`) against the database prior to starting the app[cite: 14].
2. **Configure Security & Bootstrap:**
   - Ensure Spring Security Core is configured with `ROLE_ADMIN` and `ROLE_USER` and that `Bootstrap.groovy` seeds the initial admin account[cite: 2, 3, 15].
3. **Run the Application:**
   - Start Grails (`grails run-app`) and sign in at `/login`[cite: 3].
4. **Run Tests:**
   - Execute the Spock test suite (`grails test-app`), which uses H2 for test execution and covers command validation, concurrency/stock safety, and business rule workflows[cite: 1, 15, 16, 17].

---

## Frontend & UI Conventions

- **Palette & Typography:** Soft slate blue primary (`#3B6E8F`), muted green accent (`#4F9D69`), soft amber warning (`#D98E3F`), muted red danger (`#C75C5C`), light background (`#F6F8FA`), white card surfaces (`#FFFFFF`), dark/muted slate text (`#2A2F36` / `#6B7280`), and clean sans-serif typography[cite: 8].
- **Shared GSP Partials:** `_sidebar.gsp`, `_topbar.gsp`, `_statCard.gsp`, `statusBadge.gsp`, `_errors.gsp`, and `_pagination.gsp`[cite: 8].
- **Localization:** All visible labels, headings, buttons, and validation/business error codes live in `grails-app/i18n/messages.properties` so additional locale files (such as `messages_ur.properties`) can be added without template or controller rewrites[cite: 7, 10].
