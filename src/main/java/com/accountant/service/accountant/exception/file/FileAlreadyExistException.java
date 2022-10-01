package com.accountant.service.accountant.exception.file;

public class FileAlreadyExistException extends RuntimeException{
    public FileAlreadyExistException(String message) {
        super(message);
    }
}
