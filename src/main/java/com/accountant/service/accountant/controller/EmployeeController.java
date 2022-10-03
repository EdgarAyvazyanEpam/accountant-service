package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.csv.csvservice.csvmessage.ResponseMessage;
import com.accountant.service.accountant.csv.helper.CSVEmployeeHelper;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.exception.file.FIleUploadBadRequestException;
import com.accountant.service.accountant.exception.file.FileAlreadyExistException;
import com.accountant.service.accountant.exception.handler.ApplicationExceptionHandler;
import com.accountant.service.accountant.service.interfaces.EmployeeService;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/csv")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final UploadedService uploadedService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    public EmployeeController(EmployeeService employeeService, UploadedService uploadedService) {
        this.employeeService = employeeService;
        this.uploadedService = uploadedService;
    }

    @PostMapping("/upload-employee")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            if (uploadedService.finedFileByName(file.getOriginalFilename()).isPresent()) {
                logger.info("File: " + file.getOriginalFilename() + "already exists");
                throw new FileAlreadyExistException("File already exist in database");
            }
        } catch (FileNotFoundException e) {
            logger.info("Employee file not found in database");
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Employee file not found by name: " + file.getOriginalFilename() + "!"));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
