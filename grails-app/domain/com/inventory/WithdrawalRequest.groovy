package com.inventory

class WithdrawalRequest {

    Product product
    User requestedBy
    Integer quantity
    String status // 'PENDING', 'APPROVED', 'REJECTED'
    Date requestDate = new Date()
    Date decisionDate
    User decidedBy
    String rejectionReason
    WithdrawalRequest resubmittedFrom

    static constraints = {
        product nullable: false
        requestedBy nullable: false
        quantity min: 1
        status inList: ['PENDING', 'APPROVED', 'REJECTED']
        decisionDate nullable: true
        decidedBy nullable: true
        rejectionReason nullable: true
        resubmittedFrom nullable: true
    }

    static mapping = {
        table 'withdrawal_request'
    }
}