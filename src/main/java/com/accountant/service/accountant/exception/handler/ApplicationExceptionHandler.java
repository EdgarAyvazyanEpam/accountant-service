package com.accountant.service.accountant.exception.handler;

import com.accountant.service.accountant.csv.csvservice.csvmessage.ResponseMessage;
import com.accountant.service.accountant.exception.file.FIleUploadBadRequestException;
import com.accountant.service.accountant.exception.file.FileAlreadyExistException;
import com.accountant.service.accountant.exception.salary.SalaryAlreadyCalculatedException;
import com.accountant.service.accountant.exception.currency.NoCurrencyByDateException;
import com.accountant.service.accountant.exception.currency.*;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileDtosCreationException;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileParseException;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileStoreException;
import com.accountant.service.accountant.exception.employee.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class ApplicationExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = {CSVCurrencyFileParseException.class})
    public ResponseEntity<ResponseMessage> handleCSVCurrencyFileParseException(CSVCurrencyFileParseException ex) {
        logger.info("Failed to parse currency csv fle: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {CSVCurrencyFileDtosCreationException.class})
    public ResponseEntity<ResponseMessage> handleCSVCurrencyFileDtosCreationException(CSVCurrencyFileDtosCreationException ex) {
        logger.info("Failed to convert currency csv fle to dto: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {CSVCurrencyFileStoreException.class})
    public ResponseEntity<ResponseMessage> handleCSVCurrencyFileStoreException(CSVCurrencyFileStoreException ex) {
        logger.info("Failed to store currency csv fle: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {FIleUploadBadRequestException.class})
    public ResponseEntity<ResponseMessage> handleCSVCurrencyFileUploadException(FIleUploadBadRequestException ex) {
        logger.info("Failed to upload csv fle: " + ex.getMessage() + " please check if file is correct");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {CurrencyNotFoundException.class})
    public ResponseEntity<ResponseMessage> handleCurrencyNotFoundException(CurrencyNotFoundException ex) {
        logger.info("Currency data not found: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {CSVEmployeeFileParseException.class})
    public ResponseEntity<ResponseMessage> handleCSVEmployeeFileParseException(CSVEmployeeFileParseException ex) {
        logger.info("Failed to parse Employee csv fle: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {CSVEmployeeFileDtosCreationException.class})
    public ResponseEntity<ResponseMessage> handleCSVEmployeeFileDtosCreationException(CSVEmployeeFileDtosCreationException ex) {
        logger.info("Failed to convert Employee csv fle to dto: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {CSVEmployeeFileStoreException.class})
    public ResponseEntity<ResponseMessage> handleCSVEmployeeFileStoreException(CSVEmployeeFileStoreException ex) {
        logger.info("Failed to store Employee csv fle: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(ex.getMessage()));
    }
    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    public ResponseEntity<ResponseMessage> handleCurrencyNotFoundException(EmployeeNotFoundException ex) {
        logger.info("Employee data not found: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(ex.getMessage()));
    }


    @ExceptionHandler(value = {FileAlreadyExistException.class})
    public ResponseEntity<ResponseMessage> handleFileAlreadyExistsException(FileAlreadyExistException ex) {
        logger.info("Employee data not found: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {FileNotFoundException.class})
    public ResponseEntity<ResponseMessage> handleFileNotFoundException(FileNotFoundException ex) {
        logger.info("File not found: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {NoCurrencyByDateException.class})
    public ResponseEntity<ResponseMessage> handleNoCurrencyByDateException(NoCurrencyByDateException ex) {
        logger.info("File not found: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(ex.getMessage()));
    }

    @ExceptionHandler(value = {SalaryAlreadyCalculatedException.class})
    public ResponseEntity<ResponseMessage> handleSalaryAlreadyCalculatedException(SalaryAlreadyCalculatedException ex) {
        logger.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(ex.getMessage()));
    }
}
