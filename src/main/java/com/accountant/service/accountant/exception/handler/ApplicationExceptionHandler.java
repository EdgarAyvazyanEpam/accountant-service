package com.accountant.service.accountant.exception.handler;

import com.accountant.service.accountant.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = {CSVCurrencyFileParseException.class})
    public ResponseEntity handleCSVCurrencyFileParseException(CSVCurrencyFileParseException ex) {
        logger.info("Failed to parse currency csv fle: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }

    @ExceptionHandler(value = {CSVCurrencyFileDtosCreationException.class})
    public ResponseEntity handleCSVCurrencyFileDtosCreationException(CSVCurrencyFileDtosCreationException ex) {
        logger.info("Failed to convert currency csv fle to dto: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
    }

    @ExceptionHandler(value = {CSVCurrencyFileStoreException.class})
    public ResponseEntity handleCSVCurrencyFileStoreException(CSVCurrencyFileStoreException ex) {
        logger.info("Failed to store currency csv fle: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
    }

    @ExceptionHandler(value = {FIleUploadBadRequestException.class})
    public ResponseEntity handleCSVCurrencyFileUploadException(FIleUploadBadRequestException ex) {
        logger.info("Failed to upload currency csv fle: " + ex.getMessage() + " please check if file is correct");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {CurrencyNotFoundException.class})
    public ResponseEntity handleCurrencyNotFoundException(CurrencyNotFoundException ex) {
        logger.info("Currency data not found: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }

}
