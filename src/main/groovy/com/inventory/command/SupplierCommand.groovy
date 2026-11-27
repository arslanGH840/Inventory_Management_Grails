package com.inventory.command

import grails.validation.Validateable

class SupplierCommand implements Validateable {
    Long id
    String name
    String contactEmail
    String phone

    static constraints = {
        id nullable: true
        name blank: false
        contactEmail validator: { val ->
            if (!val || !val.trim()) return 'supplier.email.invalid'
            val ==~ /^[\w.+-]+@[\w-]+\.[a-zA-Z]{2,}$/ ? true : 'supplier.email.invalid'
        }
        phone blank: false
    }
}