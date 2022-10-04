package com.accountant.service.accountant.service;

import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.entity.UploadedFileEntity;
import com.accountant.service.accountant.repository.CurrencyRepository;
import com.accountant.service.accountant.repository.EmployeeRepository;
import com.accountant.service.accountant.repository.UploadedFileRepository;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UploadedFileServiceImpl implements UploadedService {

    private final UploadedFileRepository uploadedFileRepository;
    private final CurrencyRepository currencyRepository;
    private final EmployeeRepository employeeRepository;

    public UploadedFileServiceImpl(UploadedFileRepository uploadedFileRepository, UploadedFileRepository fileRepository, CurrencyRepository currencyRepository, EmployeeRepository employeeRepository) {
        this.uploadedFileRepository = uploadedFileRepository;
        this.currencyRepository = currencyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public UploadedFileEntity saveUploadedFile(MultipartFile file) {
        try {
            UploadedFileEntity entity = new UploadedFileEntity(null, file.getOriginalFilename(), Arrays.toString(file.getBytes()), LocalDateTime.now());
            return uploadedFileRepository.save(entity);
        } catch (IOException e) {
            throw new RuntimeException("fail to store file data: " + e.getMessage());
        }
    }

    @Override
    public Optional<CurrencyEntity> deleteCurrencyFileById(Long id) throws FileNotFoundException {
        Optional<UploadedFileEntity> uploadedFileEntity = uploadedFileRepository.deleteUploadedFileEntityById(id);
        if (uploadedFileEntity.isEmpty()) {
            throw new FileNotFoundException();
        }
        return currencyRepository.deleteByFileId(String.valueOf(id));
    }

    @Override
    public Optional<EmployeeEntity> deleteEmployeeFileById(Long id) throws FileNotFoundException {
        Optional<UploadedFileEntity> uploadedFileEntity = uploadedFileRepository.deleteUploadedFileEntityById(id);
        if (uploadedFileEntity.isEmpty()) {
            throw new FileNotFoundException();
        }
        return employeeRepository.deleteByFileId(String.valueOf(id));
    }

    @Override
    public Optional<UploadedFileEntity> finedFileByName(String name) throws FileNotFoundException {
        Optional<UploadedFileEntity> byFileName = uploadedFileRepository.findByFileName(name);
        if (byFileName.isEmpty()) {
            throw new FileNotFoundException();
        }
        return byFileName;
    }

    @Override
    public Optional<UploadedFileEntity> deleteFileById(Long id) {
        return uploadedFileRepository.deleteUploadedFileEntityById(id);
    }
}
