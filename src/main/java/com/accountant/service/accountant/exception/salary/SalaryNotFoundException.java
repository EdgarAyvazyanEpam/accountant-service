package com.accountant.service.accountant.exception.salary;

public class SalaryNotFoundException extends RuntimeException{
    public SalaryNotFoundException(String message) {
        super(message);
    }
}
