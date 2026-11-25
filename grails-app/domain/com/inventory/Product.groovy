package com.inventory

class Product {
    String name
    String sku
    BigDecimal unitPrice
    Integer reorderThreshold
    Date lastRestockedDate
    Boolean active = true
    static constraints = {
        name blank: false
        sku blank: false, unique: true
        unitPrice min: 0.0
        reorderThreshold min: 0
        lastRestockedDate nullable: true
        active nullable: false
    }

    static mapping = {
        table 'product'
    }
}