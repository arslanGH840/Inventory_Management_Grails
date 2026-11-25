package com.inventory

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class StockTransactionController {

    StockTransactionService stockTransactionService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond stockTransactionService.list(params), model:[stockTransactionCount: stockTransactionService.count()]
    }

    def show(Long id) {
        respond stockTransactionService.get(id)
    }

    def create() {
        respond new StockTransaction(params)
    }

    def save(StockTransaction stockTransaction) {
        if (stockTransaction == null) {
            notFound()
            return
        }

        try {
            stockTransactionService.save(stockTransaction)
        } catch (ValidationException e) {
            respond stockTransaction.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'stockTransaction.label', default: 'StockTransaction'), stockTransaction.id])
                redirect stockTransaction
            }
            '*' { respond stockTransaction, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond stockTransactionService.get(id)
    }

    def update(StockTransaction stockTransaction) {
        if (stockTransaction == null) {
            notFound()
            return
        }

        try {
            stockTransactionService.save(stockTransaction)
        } catch (ValidationException e) {
            respond stockTransaction.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'stockTransaction.label', default: 'StockTransaction'), stockTransaction.id])
                redirect stockTransaction
            }
            '*'{ respond stockTransaction, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        stockTransactionService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'stockTransaction.label', default: 'StockTransaction'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'stockTransaction.label', default: 'StockTransaction'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
