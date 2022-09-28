package com.accountant.service.accountant.csv.csvservice;

import com.accountant.service.accountant.csv.helper.CSVCurrencyHelper;
import com.accountant.service.accountant.csv.helper.CSVEmployeeHelper;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.repository.CurrencyRepository;
import com.accountant.service.accountant.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVService {

    private final EmployeeRepository employeeRepository;
    private final CurrencyRepository currencyRepository;

    public CSVService(EmployeeRepository employeeRepository, CurrencyRepository currencyRepository) {
        this.employeeRepository = employeeRepository;
        this.currencyRepository = currencyRepository;
    }

    public void saveEmployee(MultipartFile file) {
        try {
            List<EmployeeDTO> employees = CSVEmployeeHelper.csvToEmployees(file.getInputStream(), file.getOriginalFilename());
            employeeRepository.saveAll(employeeDtosToEmployeeEntities(employees));
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeEntitiesToEmployeeDtos(employeeRepository.findAll());
    }


    public void saveCurrency(MultipartFile file) {
        try {
            List<CurrencyDTO> currencies = CSVCurrencyHelper.csvToEmployees(file.getInputStream(), file.getOriginalFilename());
            currencyRepository.saveAll(currencyDtosToCurrencyEntities(currencies));
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<CurrencyDTO> getAllCurrencies() {
        return currencyEntitiesToCurrencyDtos(currencyRepository.findAll());
    }

    private List<CurrencyEntity> currencyDtosToCurrencyEntities(List<CurrencyDTO> currencyDTOS) {
        List<CurrencyEntity> currencyEntities = new ArrayList<>();
        for (CurrencyDTO dto : currencyDTOS) {
            currencyEntities.add(currencyDtoToCurrencyEntity(dto));
        }
        return currencyEntities;
    }

    private CurrencyEntity currencyDtoToCurrencyEntity(CurrencyDTO dto) {
        CurrencyEntity entity = new CurrencyEntity();
        entity.setId(dto.getId());
        entity.setCurrencyDate(dto.getCurrencyDate());
        entity.setRate(dto.getRate());
        entity.setIsoCodeFrom(dto.getIsoCodeFrom());
        entity.setIsoCodeTo(dto.getIsoCodeTo());
        entity.setCreationDate(dto.getCreationDate());
        entity.setFileName(dto.getFileName());

        return entity;
    }

    private List<CurrencyDTO> currencyEntitiesToCurrencyDtos(List<CurrencyEntity> entities) {
        List<CurrencyDTO> currencyDTOS = new ArrayList<>();
        for (CurrencyEntity entity : entities) {
            currencyDTOS.add(currencyEntityToCurrencyDto(entity));
        }
        return currencyDTOS;
    }

    private CurrencyDTO currencyEntityToCurrencyDto(CurrencyEntity entity) {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setId(entity.getId());
        dto.setCurrencyDate(entity.getCurrencyDate());
        dto.setRate(entity.getRate());
        dto.setIsoCodeFrom(entity.getIsoCodeFrom());
        dto.setIsoCodeTo(entity.getIsoCodeTo());
        dto.setCreationDate(entity.getCreationDate());
        dto.setFileName(entity.getFileName());

        return dto;
    }

    private List<EmployeeEntity> employeeDtosToEmployeeEntities(List<EmployeeDTO> employeeDTOS) {
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        for (EmployeeDTO dto : employeeDTOS) {
            employeeEntities.add(employeeDtoToEmployeeEntity(dto));
        }
        return employeeEntities;
    }

    private EmployeeEntity employeeDtoToEmployeeEntity(EmployeeDTO dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setSalary(dto.getSalary());
        entity.setCreationDate(dto.getCreationDate());
        entity.setFileName(dto.getFileName());

        return entity;
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

        return dto;
    }
}
