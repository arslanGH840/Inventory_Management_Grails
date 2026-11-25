package com.inventory.command

import grails.validation.Validateable

class WithdrawalDecisionCommand implements Validateable {
    Long requestId
    String decision // 'APPROVE' or 'REJECT'
    String rejectionReason

    static constraints = {
        requestId nullable: false
        decision inList: ['APPROVE', 'REJECT']
        rejectionReason nullable: true, validator: { val, obj ->
            (obj.decision == 'REJECT' && !val) ? 'decision.reason.required' : true
        }
    }
}