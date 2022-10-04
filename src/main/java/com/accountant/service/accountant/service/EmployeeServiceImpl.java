package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVServiceImpl;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileParseException;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileStoreException;
import com.accountant.service.accountant.exception.employee.EmployeeNotFoundException;
import com.accountant.service.accountant.repository.EmployeeRepository;
import com.accountant.service.accountant.repository.UploadedFileRepository;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements com.accountant.service.accountant.service.interfaces.EmployeeService {
    private final CSVServiceImpl csvServiceImpl;
    private final UploadedService uploadedService;
    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

        public EmployeeServiceImpl(CSVServiceImpl csvServiceImpl, UploadedService uploadedService, EmployeeRepository employeeRepository, UploadedFileRepository uploadedFileRepository) {
        this.csvServiceImpl = csvServiceImpl;
        this.uploadedService = uploadedService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void saveEmployee(MultipartFile file) {
        List<EmployeeDTO> employeeDTOS ;
        try {
            employeeDTOS = csvServiceImpl.createEmployeeDtos(file, uploadedService.saveUploadedFile(file));
            employeeRepository.saveAll(employeeDtosToEmployeeEntities(employeeDTOS));
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
            return employeeEntitiesToEmployeeDtos(all);
        }
    }


    public List<EmployeeEntity> employeeDtosToEmployeeEntities(List<EmployeeDTO> employeeDTOS) {
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        for (EmployeeDTO dto : employeeDTOS) {
            employeeEntities.add(employeeDtoToEmployeeEntity(dto));
        }
        return employeeEntities;
    }

    public EmployeeEntity employeeDtoToEmployeeEntity(EmployeeDTO dto) {

        return new EmployeeEntity(dto.getId(), dto.getFullName(),
                dto.getSalary(), dto.getCreationDate(), dto.getFileName(), dto.getFileId());
    }

    public List<EmployeeDTO> employeeEntitiesToEmployeeDtos(List<EmployeeEntity> entities) {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (EmployeeEntity entity : entities) {
            employeeDTOS.add(employeeEntityToEmployeeDto(entity));
        }
        return employeeDTOS;
    }

    public EmployeeDTO employeeEntityToEmployeeDto(EmployeeEntity entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setSalary(entity.getSalary());
        dto.setCreationDate(entity.getCreationDate());
        dto.setFileName(entity.getFileName());
        dto.setFileId(entity.getFileId());

        return dto;
    }
}
