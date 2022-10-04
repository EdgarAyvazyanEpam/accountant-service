package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVService;
import com.accountant.service.accountant.csv.csvservice.CSVServiceImpl;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileParseException;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileStoreException;
import com.accountant.service.accountant.exception.employee.EmployeeNotFoundException;
import com.accountant.service.accountant.repository.EmployeeRepository;
import com.accountant.service.accountant.repository.UploadedFileRepository;
import com.accountant.service.accountant.service.helper.EmployeeHelper;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EmployeeServiceImpl implements com.accountant.service.accountant.service.interfaces.EmployeeService {
    private final CSVService csvService;
    private final UploadedService uploadedService;
    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

        public EmployeeServiceImpl(CSVService csvService, UploadedService uploadedService, EmployeeRepository employeeRepository) {
        this.csvService = csvService;
        this.uploadedService = uploadedService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeEntity> saveEmployee(MultipartFile file) {
        List<EmployeeDTO> employeeDTOS ;
        try {
            employeeDTOS = csvService.createEmployeeDtos(file, uploadedService.saveUploadedFile(file));
            return employeeRepository.saveAll(EmployeeHelper.employeeDtosToEmployeeEntities(employeeDTOS));
        } catch (CSVEmployeeFileParseException e) {
            String message = "Fail to store CSV file:";
            logger.error(message, e);
            throw new CSVEmployeeFileStoreException(message);
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> all = employeeRepository.findAll();
        if (all.isEmpty()) {
            throw new EmployeeNotFoundException("No employee data");
        }else {
            return EmployeeHelper.employeeEntitiesToEmployeeDtos(all);
        }
    }
}
