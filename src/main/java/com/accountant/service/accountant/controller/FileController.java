package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.csv.csvservice.csvmessage.ResponseMessage;
import com.accountant.service.accountant.repository.UploadedFileRepository;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/csv/file")
public class FileController {
    private final UploadedService uploadedService;

    public FileController(UploadedFileRepository uploadedFileRepository, UploadedService uploadedService) {
        this.uploadedService = uploadedService;
    }

    @DeleteMapping(value = "/delete-currency")
    public ResponseEntity<ResponseMessage> deleteUploadedCurrencyFile(@RequestParam(value = "fileName") String fileName) {

        String message = "";
        if (fileName.contains("History")) {
            try {
                Integer id = uploadedService.deleteCurrencyFileByName(fileName);
                if (id > 0) {
                    message = "File deleted successfully by name: " + fileName;
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
                }
            } catch (Exception e) {
                message = "Could not delete the file name: " + fileName + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please correct a csv file or file name and try again!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @DeleteMapping(value = "/delete-employee")
    public ResponseEntity<ResponseMessage> deleteUploadedEmployeeFile(@RequestParam(value = "fileName") String fileName) {

        String message = "";
        if (fileName.contains("Employee")) {
            try {
                Integer id = uploadedService.deleteEmployeeFileByName(fileName);
                if (id > 0) {
                    message = "File deleted successfully by name: " + fileName;
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
                }
            } catch (Exception e) {
                message = "Could not delete the file name: " + fileName + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please correct a csv file name and try again!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
