package com.accountant.service.accountant.csv.csvservice;

import com.accountant.service.accountant.csv.helper.CSVCurrencyHelper;
import com.accountant.service.accountant.csv.helper.CSVEmployeeHelper;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.UploadedFileEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {

    public List<EmployeeDTO> saveEmployee(MultipartFile file, UploadedFileEntity uploadedFileEntity) {
        try {
            return CSVEmployeeHelper.csvToEmployees(file.getInputStream(), file.getOriginalFilename(),
                    uploadedFileEntity.getId());
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }



    public List<CurrencyDTO> saveCurrency(MultipartFile file, UploadedFileEntity uploadedFileEntity) {
        try {
            return CSVCurrencyHelper.csvToCurrencies(file.getInputStream(), file.getOriginalFilename(),
                    uploadedFileEntity.getId());
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

}
