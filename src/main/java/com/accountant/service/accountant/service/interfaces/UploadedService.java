package com.accountant.service.accountant.service.interfaces;

import com.accountant.service.accountant.entity.UploadedFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UploadedService {
    UploadedFileEntity saveUploadedFile(MultipartFile file);
    Long deleteCurrencyFileById(Long id);
    Long deleteEmployeeFileById(Long id);
    Optional<UploadedFileEntity> finedFileByName(String name);
}
