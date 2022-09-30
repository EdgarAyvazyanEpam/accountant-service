package com.accountant.service.accountant.service;

import com.accountant.service.accountant.entity.UploadedFileEntity;
import com.accountant.service.accountant.repository.CurrencyRepository;
import com.accountant.service.accountant.repository.EmployeeRepository;
import com.accountant.service.accountant.repository.UploadedFileRepository;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UploadedFileService implements UploadedService {

    private final UploadedFileRepository uploadedFileRepository;
    private final CurrencyRepository currencyRepository;
    private final EmployeeRepository employeeRepository;

    public UploadedFileService(UploadedFileRepository uploadedFileRepository, UploadedFileRepository fileRepository, CurrencyRepository currencyRepository, EmployeeRepository employeeRepository) {
        this.uploadedFileRepository = uploadedFileRepository;
        this.currencyRepository = currencyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UploadedFileEntity saveUploadedFile(MultipartFile file) {
        try {
            UploadedFileEntity entity = new UploadedFileEntity(file.getOriginalFilename(), Arrays.toString(file.getBytes()), LocalDateTime.now());
            return uploadedFileRepository.save(entity);
        } catch (IOException e) {
            throw new RuntimeException("fail to store file data: " + e.getMessage());
        }
    }

    @Override
    public Long deleteCurrencyFileById(Long id) {
        uploadedFileRepository.deleteUploadedFileEntityById(id);
        return currencyRepository.deleteByFileId(String.valueOf(id));
    }

    @Override
    public Long deleteEmployeeFileById(Long id) {
        uploadedFileRepository.deleteUploadedFileEntityById(id);
        return employeeRepository.deleteByFileId(String.valueOf(id));
    }

    @Override
    public Optional<UploadedFileEntity> finedFileByName(String name) {
        return uploadedFileRepository.findByFileName(name);
    }
}
