package com.inventory

import com.inventory.command.WithdrawalDecisionCommand
import com.inventory.command.WithdrawalRequestCommand
import grails.gorm.transactions.Transactional

@Transactional
class WithdrawalRequestService {

    StockService stockService

    List<WithdrawalRequest> pending(Map params = [:]) {
        WithdrawalRequest.createCriteria().list(max: params.max, offset: params.offset ?: 0) {
            eq('status', 'PENDING')
            order('requestDate', 'asc')
        }
    }

    Long pendingCount() {
        WithdrawalRequest.countByStatus('PENDING')
    }

    List<WithdrawalRequest> myRequests(User user) {
        WithdrawalRequest.createCriteria().list {
            eq('requestedBy', user)
            order('requestDate', 'desc')
        }
    }

    WithdrawalRequest submit(WithdrawalRequestCommand cmd, User user) {
        new WithdrawalRequest(
                product: Product.get(cmd.productId),
                requestedBy: user,
                quantity: cmd.quantity,
                status: 'PENDING'
        ).save(failOnError: true, flush: true)
    }

    Map decide(WithdrawalDecisionCommand cmd, User admin) {
        cmd.decision == 'APPROVE' ? approve(cmd, admin) : reject(cmd, admin)
    }

    // Available stock is re-checked at approval time, inside the same
    // locked transaction as the deduction — never trusted from the time
    // the request was submitted (Technical PRD §1, "Stock safety rule").
    private Map approve(WithdrawalDecisionCommand cmd, User admin) {
        WithdrawalRequest request = WithdrawalRequest.lock(cmd.requestId) // pessimistic row lock
        if (!request) return [success: false, code: 'default.not.found.message']
        if (request.status != 'PENDING') return [success: false, code: 'withdrawal.already.decided']

        Integer available = stockService.getCurrentStock(request.product.id)
        if (available < request.quantity) return [success: false, code: 'stock.insufficient']

        new StockTransaction(
                product: request.product,
                quantity: request.quantity,
                transactionType: 'OUT',
                transactionDate: new Date(),
                sourceRequest: request
        ).save(failOnError: true)

        request.status = 'APPROVED'
        request.decidedBy = admin
        request.decisionDate = new Date()
        request.save(failOnError: true, flush: true)

        [success: true]
    }

    private Map reject(WithdrawalDecisionCommand cmd, User admin) {
        WithdrawalRequest request = WithdrawalRequest.lock(cmd.requestId)
        if (!request) return [success: false, code: 'default.not.found.message']
        if (request.status != 'PENDING') return [success: false, code: 'withdrawal.already.decided']

        request.status = 'REJECTED'
        request.decidedBy = admin
        request.decisionDate = new Date()
        request.rejectionReason = cmd.rejectionReason
        request.save(failOnError: true, flush: true)

        [success: true]
    }
}