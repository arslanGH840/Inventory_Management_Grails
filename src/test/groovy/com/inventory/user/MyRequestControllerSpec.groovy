package com.inventory.user

import com.inventory.Product
import com.inventory.User
import com.inventory.WithdrawalRequestService
import com.inventory.command.WithdrawalRequestCommand
import grails.plugin.springsecurity.SpringSecurityService
import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class MyRequestControllerSpec extends Specification implements ControllerUnitTest<MyRequestController>, DataTest {

    def setupSpec() {
        mockDomains(Product)
    }

    void "index shows only the current user's requests"() {
        given:
        User user = new User(username: 'alice', password: 'pass')
        controller.springSecurityService = Mock(SpringSecurityService) {
            getCurrentUser() >> user
        }
        controller.withdrawalRequestService = Mock(WithdrawalRequestService) {
            1 * myRequests(user) >> []
        }

        when:
        def model = controller.index()

        then:
        model.requests == []
    }

    void "submit redisplays the create form with an error when the command is invalid"() {
        given:
        WithdrawalRequestCommand cmd = new WithdrawalRequestCommand(productId: 999L, quantity: -1)
        cmd.validate()

        when:
        request.method = 'POST'
        controller.submit(cmd)

        then:
        response.redirectedUrl?.contains('create')
        flash.message != null
    }

    void "submit saves and redirects to index when the command is valid"() {
        given:
        Product product = new Product(name: 'Widget', sku: 'SKU-W1', unitPrice: 1.0, reorderThreshold: 1, active: true)
                .save(flush: true, failOnError: true)
        User user = new User(username: 'alice', password: 'pass')
        controller.springSecurityService = Mock(SpringSecurityService) {
            getCurrentUser() >> user
        }
        controller.withdrawalRequestService = Mock(WithdrawalRequestService) {
            1 * submit(_, user)
        }
        WithdrawalRequestCommand cmd = new WithdrawalRequestCommand(productId: product.id, quantity: 2)
        cmd.validate()

        when:
        request.method = 'POST'
        controller.submit(cmd)

        then:
        response.redirectedUrl == '/myRequest/index'
        flash.message != null
    }
}