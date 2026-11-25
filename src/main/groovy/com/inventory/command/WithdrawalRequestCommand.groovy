package com.inventory.command

import com.inventory.Product
import grails.validation.Validateable

class WithdrawalRequestCommand implements Validateable {
    Long productId
    Integer quantity

    static constraints = {
        productId nullable: false, validator: { val ->
            Product.get(val)?.active ? true : 'withdrawal.product.notfound'
        }
        quantity min: 1, validator: { val ->
            (val != null && val > 0) ? true : 'withdrawal.quantity.invalid'
        }
    }
}