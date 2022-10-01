package com.accountant.service.accountant.service.interfaces;

import com.accountant.service.accountant.domain.EmployeeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    void saveEmployee(MultipartFile file);

    List<EmployeeDTO> getAllEmployees();
}
