package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.csv.csvservice.csvmessage.ResponseMessage;
import com.accountant.service.accountant.csv.helper.CSVCurrencyHelper;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.exception.FIleUploadBadRequestException;
import com.accountant.service.accountant.exception.handler.ApplicationExceptionHandler;
import com.accountant.service.accountant.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/csv")
public class CurrencyController {
    private final CurrencyService currencyService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/upload-currency")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

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

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies() {

        List<CurrencyDTO> currencyEntities = currencyService.getAllCurrencies();
        return new ResponseEntity<>(currencyEntities, HttpStatus.OK);

    }
}
