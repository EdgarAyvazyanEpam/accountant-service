package com.accountant.service.accountant.exception;

public class FileAlreadyExistException extends RuntimeException{
    public FileAlreadyExistException(String message) {
        super(message);
    }
}
