package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.csv.csvservice.csvmessage.ResponseMessage;
import com.accountant.service.accountant.csv.helper.CSVCurrencyHelper;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.exception.file.FIleUploadBadRequestException;
import com.accountant.service.accountant.exception.file.FileAlreadyExistException;
import com.accountant.service.accountant.exception.handler.ApplicationExceptionHandler;
import com.accountant.service.accountant.service.CurrencyService;
import com.accountant.service.accountant.service.UploadedFileService;
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
public class CurrencyController {
    private final CurrencyService currencyService;
    private final UploadedFileService uploadedFileService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    public CurrencyController(CurrencyService currencyService, UploadedService uploadedService, UploadedFileService uploadedFileService) {
        this.currencyService = currencyService;
        this.uploadedFileService = uploadedFileService;
    }

    @PostMapping("/upload-currency")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            if (uploadedFileService.finedFileByName(file.getOriginalFilename()).isPresent()) {
                logger.info("File: " + file.getOriginalFilename() + "already exists");
                throw new FileAlreadyExistException("File already exist in database");
            }
        } catch (FileNotFoundException e) {
            logger.info("Currency file not found in database");
            if (CSVCurrencyHelper.hasCSVFormat(file)) {
                currencyService.saveCurrency(file);
                logger.info("Uploaded the file successfully: " + file.getOriginalFilename());
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Uploaded the file successfully: "));
            } else {
                String message = "Failed to upload currency csv fle: " + file.getOriginalFilename() + " please check if file is correct";
                logger.error(message);
                throw new FIleUploadBadRequestException(message);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Currency file not found by name: " + file.getOriginalFilename() + "!"));
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies() {

        List<CurrencyDTO> currencies = currencyService.getAllCurrencies();
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }
}
