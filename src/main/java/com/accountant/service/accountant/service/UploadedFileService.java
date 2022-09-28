package com.accountant.service.accountant.service;

import com.accountant.service.accountant.entity.UploadedFileEntity;
import com.accountant.service.accountant.repository.UploadedFileRepository;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@Service
public class UploadedFileService implements UploadedService {

    private final UploadedFileRepository uploadedFileRepository;
    private final UploadedFileRepository fileRepository;

    public UploadedFileService(UploadedFileRepository uploadedFileRepository, UploadedFileRepository fileRepository) {
        this.uploadedFileRepository = uploadedFileRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public UploadedFileEntity saveUploadedFile(MultipartFile file) {
        try {
            UploadedFileEntity entity = new UploadedFileEntity(file.getOriginalFilename(), Arrays.toString(file.getBytes()), new Date());
            return fileRepository.save(entity);
        } catch (IOException e) {
            throw new RuntimeException("fail to store file data: " + e.getMessage());
        }
    }

    @Override
    public Integer deleteCurrencyFileByName(String fileName) {
        return uploadedFileRepository.deleteCurrencyFileByName(fileName);
    }

    @Override
    public Integer deleteEmployeeFileByName(String fileName) {
        return uploadedFileRepository.deleteEmployeeFileByName(fileName);
    }
}
