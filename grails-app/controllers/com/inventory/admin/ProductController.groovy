package com.inventory.admin

import com.inventory.BusinessException
import com.inventory.Product
import com.inventory.ProductService
import com.inventory.StockService
import com.inventory.Supplier
import com.inventory.command.RestockCommand
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN'])
class ProductController {

    ProductService productService
    StockService stockService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", doRestock: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond productService.list(params), model: [productCount: productService.count()]
    }

    def show(Long id) {
        respond productService.get(id)
    }

    def create() {
        respond new Product(params)
    }

    def save(Product product) {
        if (product == null) {
            notFound()
            return
        }
        try {
            productService.save(product)
        } catch (ValidationException e) {
            respond product.errors, view: 'create'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'product.label', default: 'Product'), product.id])
                redirect product
            }
            '*' { respond product, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond productService.get(id)
    }

    def update(Product product) {
        if (product == null) {
            notFound()
            return
        }
        try {
            productService.save(product)
        } catch (ValidationException e) {
            respond product.errors, view: 'edit'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'product.label', default: 'Product'), product.id])
                redirect product
            }
            '*' { respond product, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        productService.delete(id) // soft delete — see ProductService
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'product.label', default: 'Product'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    def viewStock(Long id) {
        Product product = productService.get(id)
        Integer currentStock = stockService.getCurrentStock(id)
        render(view: "stock", model: [product: product, currentStock: currentStock])
    }

    def restockForm(Long id) {
        Product product = productService.get(id)
        List<Supplier> suppliers = Supplier.findAllByActive(true)
        render(view: "restockForm", model: [product: product, suppliers: suppliers])
    }

    def doRestock(RestockCommand cmd) {
        if (cmd.hasErrors()) {
            flash.message = message(code: cmd.errors.allErrors[0].defaultMessage ?: 'restock.quantity.invalid')
            redirect(action: "restockForm", id: cmd.productId)
            return
        }
        try {
            stockService.processRestock(cmd)
        } catch (BusinessException e) {
            flash.message = message(code: e.code)
            redirect(action: "restockForm", id: cmd.productId)
            return
        }
        flash.message = message(code: 'restock.success')
        redirect(action: "viewStock", id: cmd.productId)
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}