package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.csv.csvservice.csvmessage.ResponseMessage;
import com.accountant.service.accountant.csv.helper.CSVEmployeeHelper;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.exception.FIleUploadBadRequestException;
import com.accountant.service.accountant.exception.FileAlreadyExistException;
import com.accountant.service.accountant.exception.handler.ApplicationExceptionHandler;
import com.accountant.service.accountant.service.EmployeeService;
import com.accountant.service.accountant.service.UploadedFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/csv")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final UploadedFileService uploadedFileService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    public EmployeeController(EmployeeService employeeService, UploadedFileService uploadedFileService) {
        this.employeeService = employeeService;
        this.uploadedFileService = uploadedFileService;
    }

    @PostMapping("/upload-employee")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

        if (uploadedFileService.finedFileByName(file.getOriginalFilename()).isPresent()) {
            logger.info("File: " + file.getOriginalFilename() + "already exists");
            throw new FileAlreadyExistException("File already exist in database");
        } else {
            if (CSVEmployeeHelper.hasCSVFormat(file)) {
                employeeService.saveEmployee(file);
                logger.info("Uploaded the file successfully: " + file.getOriginalFilename());
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded the file successfully: "));
            } else {
                String message = "Failed to upload currency csv fle: " + file.getOriginalFilename() + " please check if file is correct";
                logger.error(message);
                throw new FIleUploadBadRequestException(message);
            }
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
