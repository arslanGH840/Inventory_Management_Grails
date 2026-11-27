package com.inventory.command

import com.inventory.Product
import grails.validation.Validateable

class ProductCommand implements Validateable {
    Long id
    String name
    String sku
    BigDecimal unitPrice
    Integer reorderThreshold

    static constraints = {
        id nullable: true
        name blank: false
        sku blank: false, validator: { val, obj ->
            def existing = Product.findBySku(val)
            (existing && existing.id != obj.id) ? 'product.sku.duplicate' : true
        }
        unitPrice validator: { val ->
            (val != null && val >= 0) ? true : 'product.unitPrice.invalid'
        }
        reorderThreshold min: 0
    }
}