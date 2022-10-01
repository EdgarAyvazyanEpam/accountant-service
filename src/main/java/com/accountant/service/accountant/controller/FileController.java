package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.csv.csvservice.csvmessage.ResponseMessage;
import com.accountant.service.accountant.exception.handler.ApplicationExceptionHandler;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("api/csv/file")
public class FileController {
    private final UploadedService uploadedService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    public FileController(UploadedService uploadedService) {
        this.uploadedService = uploadedService;
    }

    @DeleteMapping(value = "/delete-currency/{id}")
    public ResponseEntity<ResponseMessage> deleteUploadedCurrencyFile(@PathVariable("id") Long id) {

        try {
            uploadedService.deleteCurrencyFileById(id);
            logger.info("File deleted successfully by id: " + id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("File deleted successfully by id: " + id));
        } catch (FileNotFoundException e) {
            logger.info("Could not delete the file by id: " + id + "!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Could not delete the file by id: " + id + "!"));
        }
    }

    @DeleteMapping(value = "/delete-employee/")
    public ResponseEntity<ResponseMessage> deleteUploadedEmployeeFile(@RequestParam(name = "id") Long id) {

        try {
            uploadedService.deleteEmployeeFileById(id);
            logger.info("File deleted successfully by id: " + id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("File deleted successfully by id: " + id));
        } catch (FileNotFoundException e) {
            logger.info("Could not delete the file by id: " + id + "!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Could not delete the file by id: " + id + "!"));
        }
    }
}
