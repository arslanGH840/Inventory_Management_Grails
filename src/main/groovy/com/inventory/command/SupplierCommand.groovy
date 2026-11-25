package com.inventory.command

import grails.validation.Validateable

class SupplierCommand implements Validateable {
    Long id
    String name
    String contactEmail
    String phone

    static constraints = {
        name blank: false
        contactEmail blank: false, email: true, validator: { val ->
            val ? true : 'supplier.email.invalid'
        }
        phone blank: false
    }
}