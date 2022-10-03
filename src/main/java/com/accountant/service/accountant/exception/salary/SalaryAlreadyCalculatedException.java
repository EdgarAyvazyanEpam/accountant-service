package com.accountant.service.accountant.exception.salary;

public class SalaryAlreadyCalculatedException extends RuntimeException{
    public SalaryAlreadyCalculatedException(String s) {
        super(s);
    }
}
