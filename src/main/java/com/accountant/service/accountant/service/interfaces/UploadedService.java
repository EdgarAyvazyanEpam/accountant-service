package com.accountant.service.accountant.service.interfaces;

import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.entity.UploadedFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Optional;

public interface UploadedService {
    UploadedFileEntity saveUploadedFile(MultipartFile file);

    Optional<CurrencyEntity> deleteCurrencyFileById(Long id) throws FileNotFoundException;

    Optional<EmployeeEntity> deleteEmployeeFileById(Long id) throws FileNotFoundException;

    Optional<UploadedFileEntity> finedFileByName(String name) throws FileNotFoundException;
}
