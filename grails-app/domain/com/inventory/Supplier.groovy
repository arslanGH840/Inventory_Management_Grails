package com.inventory

class Supplier {
    String name
    String contactEmail
    String phone
    Boolean active = true
    static constraints = {
        name blank: false
        contactEmail email: true, blank: false
        phone blank: false
        active nullable: false
    }

    static mapping = {
        table 'supplier'
    }
}
