package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.csv.csvservice.csvmessage.ResponseMessage;
import com.accountant.service.accountant.repository.UploadedFileRepository;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/csv/file")
public class FileController {
    private final UploadedService uploadedService;

    public FileController(UploadedFileRepository uploadedFileRepository, UploadedService uploadedService) {
        this.uploadedService = uploadedService;
    }

    @DeleteMapping(value = "/delete-currency/{id}")
    public ResponseEntity<ResponseMessage> deleteUploadedCurrencyFile(@PathVariable("id") Long id) {

        String message = "";
        try {
            uploadedService.deleteCurrencyFileById(id);
            message = "File deleted successfully by id: " + id;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete the file by id: " + id + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
//        message = "Please correct a csv file or file name and try again!";
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @DeleteMapping(value = "/delete-employee/")
    public ResponseEntity<ResponseMessage> deleteUploadedEmployeeFile(@RequestParam(name = "id") Long id) {

        String message = "";
        try {
            uploadedService.deleteEmployeeFileById(id);
            message = "File deleted successfully by id: " + id;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete the file by id: " + id + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
//        message = "Please correct a csv file name and try again!";
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
