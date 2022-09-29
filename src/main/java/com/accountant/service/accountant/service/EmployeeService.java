package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVService;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileParseException;
import com.accountant.service.accountant.exception.employee.CSVEmployeeFileStoreException;
import com.accountant.service.accountant.exception.employee.EmployeeNotFoundException;
import com.accountant.service.accountant.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements com.accountant.service.accountant.service.interfaces.EmployeeService {
    private final CSVService csvService;
    private final UploadedFileService uploadedFileService;
    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(CSVService csvService, UploadedFileService uploadedFileService, EmployeeRepository employeeRepository) {
        this.csvService = csvService;
        this.uploadedFileService = uploadedFileService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void saveEmployee(MultipartFile file) {
        List<EmployeeDTO> employeeDTOS ;
        try {
            employeeDTOS = csvService.createEmployeeDtos(file, uploadedFileService.saveUploadedFile(file));
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


    private List<EmployeeEntity> employeeDtosToEmployeeEntities(List<EmployeeDTO> employeeDTOS) {
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        for (EmployeeDTO dto : employeeDTOS) {
            employeeEntities.add(employeeDtoToEmployeeEntity(dto));
        }
        return employeeEntities;
    }

    private EmployeeEntity employeeDtoToEmployeeEntity(EmployeeDTO dto) {

        return new EmployeeEntity(dto.getId(), dto.getFullName(),
                dto.getSalary(), dto.getCreationDate(), dto.getFileName(), dto.getFileId());
    }

    private List<EmployeeDTO> employeeEntitiesToEmployeeDtos(List<EmployeeEntity> entities) {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (EmployeeEntity entity : entities) {
            employeeDTOS.add(employeeEntityToEmployeeDto(entity));
        }
        return employeeDTOS;
    }

    private EmployeeDTO employeeEntityToEmployeeDto(EmployeeEntity entity) {
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
