package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVService;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EmployeeService {
    private final CSVService csvService;

    public EmployeeService(CSVService csvService) {
        this.csvService = csvService;
    }

    public void saveEmployee(MultipartFile file) {
        csvService.saveEmployee(file);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return csvService.getAllEmployees();
    }
}
