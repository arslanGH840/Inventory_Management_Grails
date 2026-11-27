package com.inventory

import com.inventory.admin.StockTransactionController
import grails.testing.gorm.DomainUnitTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.*

class StockTransactionControllerSpec extends Specification implements ControllerUnitTest<StockTransactionController>, DomainUnitTest<StockTransaction> {

    void "Test the index action returns the correct model"() {
        given:
        controller.stockTransactionService = Mock(StockTransactionService) {
            1 * search(_, _) >> []
            1 * searchCount(_) >> 0
        }

        when:
        controller.index()

        then:
        !model.stockTransactionList
        model.stockTransactionCount == 0
    }

    void "Test the show action with a null id"() {
        given:
        controller.stockTransactionService = Mock(StockTransactionService) {
            1 * get(null) >> null
        }

        when:
        controller.show(null)

        then:
        response.status == 404
    }

    void "Test the show action with a valid id"() {
        given:
        controller.stockTransactionService = Mock(StockTransactionService) {
            1 * get(2) >> new StockTransaction()
        }

        when:
        controller.show(2)

        then:
        model.stockTransaction instanceof StockTransaction
    }
}