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

    // A rejected request may be resubmitted exactly once. The resubmission
    // is a brand-new PENDING request linked via resubmittedFrom; if THAT
    // one is also rejected, no further resubmission is allowed — the user
    // must contact an Admin directly (Technical PRD §2.6.6).
    Map resubmit(Long requestId, User user) {
        WithdrawalRequest original = WithdrawalRequest.get(requestId)
        if (!original || original.requestedBy?.id != user.id) {
            return [success: false, code: 'default.not.found.message']
        }
        if (original.status != 'REJECTED') {
            return [success: false, code: 'withdrawal.resubmit.notRejected']
        }
        if (original.resubmittedFrom != null) {
            // `original` is itself already a resubmission that was rejected again.
            return [success: false, code: 'withdrawal.resubmit.notAllowed']
        }
        if (WithdrawalRequest.countByResubmittedFrom(original) > 0) {
            return [success: false, code: 'withdrawal.resubmit.alreadyResubmitted']
        }

        WithdrawalRequest resubmission = new WithdrawalRequest(
                product: original.product,
                requestedBy: user,
                quantity: original.quantity,
                status: 'PENDING',
                resubmittedFrom: original
        ).save(failOnError: true, flush: true)

        [success: true, request: resubmission]
    }

    Map decide(WithdrawalDecisionCommand cmd, User admin) {
        cmd.decision == 'APPROVE' ? approve(cmd, admin) : reject(cmd, admin)
    }

    private Map approve(WithdrawalDecisionCommand cmd, User admin) {
        WithdrawalRequest request = WithdrawalRequest.lock(cmd.requestId)
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