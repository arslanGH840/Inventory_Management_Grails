package com.inventory.command

import com.inventory.Product
import com.inventory.Supplier
import grails.validation.Validateable

class RestockCommand implements Validateable {
    Long productId
    Long supplierId
    Integer quantity

    static constraints = {
        productId nullable: false, validator: { val ->
            Product.get(val) ? true : 'restock.product.notfound'
        }
        supplierId nullable: false, validator: { val ->
            Supplier.get(val) ? true : 'restock.supplier.notfound'
        }
        quantity validator: { val ->
            (val != null && val > 0) ? true : 'restock.quantity.invalid'
        }
    }
}