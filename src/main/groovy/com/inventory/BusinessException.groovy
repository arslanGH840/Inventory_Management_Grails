package com.inventory

class BusinessException extends RuntimeException {
    String code

    BusinessException(String code) {
        super(code)
        this.code = code
    }

    BusinessException(String code, Throwable cause) {
        super(code, cause)
        this.code = code
    }
}