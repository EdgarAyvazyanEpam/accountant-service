package com.accountant.service.accountant.service.interfaces;

import com.accountant.service.accountant.entity.UploadedFileEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadedService {
    UploadedFileEntity saveUploadedFile(MultipartFile file);
}
