package com.inventory.command

import grails.testing.gorm.DataTest
import spock.lang.Specification

class WithdrawalDecisionCommandSpec extends Specification implements DataTest {

    def setupSpec() {
        mockDomains()
    }

    void "fails with decision.reason.required when rejecting without a reason"() {
        given:
        def cmd = new WithdrawalDecisionCommand(requestId: 1L, decision: 'REJECT', rejectionReason: null)

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('rejectionReason').code == 'decision.reason.required'
    }

    void "passes when approving with no reason"() {
        given:
        def cmd = new WithdrawalDecisionCommand(requestId: 1L, decision: 'APPROVE', rejectionReason: null)

        expect:
        cmd.validate()
    }

    void "passes when rejecting with a reason"() {
        given:
        def cmd = new WithdrawalDecisionCommand(requestId: 1L, decision: 'REJECT', rejectionReason: 'Out of budget')

        expect:
        cmd.validate()
    }
}