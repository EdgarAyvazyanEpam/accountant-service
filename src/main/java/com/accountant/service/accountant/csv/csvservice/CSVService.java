package com.accountant.service.accountant.csv.csvservice;

import com.accountant.service.accountant.csv.helper.CSVCurrencyHelper;
import com.accountant.service.accountant.csv.helper.CSVEmployeeHelper;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.repository.CurrencyRepository;
import com.accountant.service.accountant.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            List<EmployeeEntity> employees = CSVEmployeeHelper.csvToEmployees(file.getInputStream(), file.getOriginalFilename());
            employeeRepository.saveAll(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public void saveCurrency(MultipartFile file) {
        try {
            List<CurrencyEntity> currencies = CSVCurrencyHelper.csvToEmployees(file.getInputStream(), file.getOriginalFilename());
            currencyRepository.saveAll(currencies);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<CurrencyEntity> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
