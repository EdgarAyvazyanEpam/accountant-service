package com.accountant.service.accountant.exception.currency;

public class NoCurrencyByDateException extends RuntimeException{
    public NoCurrencyByDateException(String message) {
        super(message);
    }
}
