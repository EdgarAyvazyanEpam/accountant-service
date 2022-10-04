package com.accountant.service.accountant.service.interfaces;

import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.EmployeeEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    List<EmployeeEntity> saveEmployee(MultipartFile file);

    List<EmployeeDTO> getAllEmployees();
}
