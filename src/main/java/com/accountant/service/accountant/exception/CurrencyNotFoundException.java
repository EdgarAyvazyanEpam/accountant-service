package com.accountant.service.accountant.exception;

public class CurrencyNotFoundException extends RuntimeException{

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
