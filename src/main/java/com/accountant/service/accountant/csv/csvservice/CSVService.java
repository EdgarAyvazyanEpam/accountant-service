package com.accountant.service.accountant.csv.csvservice;

import com.accountant.service.accountant.csv.helper.CSVCurrencyHelper;
import com.accountant.service.accountant.csv.helper.CSVEmployeeHelper;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.UploadedFileEntity;
import com.accountant.service.accountant.exception.currency.CSVCurrencyFileDtosCreationException;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileDtosCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    private static final Logger logger = LoggerFactory.getLogger(CSVService.class);

    public List<EmployeeDTO> createEmployeeDtos(MultipartFile file, UploadedFileEntity uploadedFileEntity) {
        try {
            return CSVEmployeeHelper.csvToEmployees(file.getInputStream(), file.getOriginalFilename(),
                    uploadedFileEntity.getId());
        } catch (IOException e) {
            String message = "Fail to create employee dtos";
            logger.error(message, e);
            throw new CSVEmployeeFileDtosCreationException(message);
        }
    }



    public List<CurrencyDTO> createCurrencyDtos(MultipartFile file, UploadedFileEntity uploadedFileEntity) {
        try {
            return CSVCurrencyHelper.csvToCurrencies(file.getInputStream(), file.getOriginalFilename(),
                    uploadedFileEntity.getId());
        } catch (IOException e) {
            String message = "Fail to create currency dtos";
            logger.error(message, e);
            throw new CSVCurrencyFileDtosCreationException(message);
        }
    }

}
