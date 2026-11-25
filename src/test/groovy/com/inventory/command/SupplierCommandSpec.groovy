package com.inventory.command

import grails.testing.gorm.DataTest
import spock.lang.Specification

class SupplierCommandSpec extends Specification implements DataTest {

    def setupSpec() {
        mockDomains()
    }

    void "fails with supplier.email.invalid on a malformed email"() {
        given:
        def cmd = new SupplierCommand(name: 'Acme', contactEmail: 'not-an-email', phone: '1234567890')

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('contactEmail')
    }

    void "fails when phone is blank"() {
        given:
        def cmd = new SupplierCommand(name: 'Acme', contactEmail: 'ok@example.com', phone: '')

        expect:
        !cmd.validate()
        cmd.errors.getFieldError('phone')
    }

    void "passes with valid fields"() {
        given:
        def cmd = new SupplierCommand(name: 'Acme', contactEmail: 'ok@example.com', phone: '1234567890')

        expect:
        cmd.validate()
    }
}