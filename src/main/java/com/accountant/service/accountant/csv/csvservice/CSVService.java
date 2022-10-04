package com.accountant.service.accountant.csv.csvservice;

import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.UploadedFileEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CSVService {
    List<EmployeeDTO> createEmployeeDtos(MultipartFile file, UploadedFileEntity uploadedFileEntity);
    List<CurrencyDTO> createCurrencyDtos(MultipartFile file, UploadedFileEntity uploadedFileEntity);
}
