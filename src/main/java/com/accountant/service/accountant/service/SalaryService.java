package com.accountant.service.accountant.service;

import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.domain.SalaryDto;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.SalaryEntity;
import com.accountant.service.accountant.exception.salary.SalaryAlreadyCalculatedException;
import com.accountant.service.accountant.exception.currency.NoCurrencyByDateException;
import com.accountant.service.accountant.exception.handler.ApplicationExceptionHandler;
import com.accountant.service.accountant.repository.SalaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class SalaryService implements com.accountant.service.accountant.service.interfaces.SalaryService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);
    private final EmployeeService employeeService;
    private final CurrencyService currencyService;
    private final SalaryRepository salaryRepository;

    public SalaryService(EmployeeService employeeService, CurrencyService currencyService, SalaryRepository salaryRepository) {
        this.employeeService = employeeService;
        this.currencyService = currencyService;
        this.salaryRepository = salaryRepository;
    }

    public List<SalaryDto> calculateSalary(LocalDate localDate) {
        List<SalaryDto> salaryDtos = new ArrayList<>();
        if (salaryRepository.getSalaryEntityByCurrencyDate(localDate.atStartOfDay()).isPresent() ||
                currencyService.getCurrencyByClosestDate(localDate.atStartOfDay()).isPresent()) {
            logger.info("Salary calculated for this date:" + localDate);
            throw new SalaryAlreadyCalculatedException();
        } else {
            List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();
            Optional<CurrencyEntity> currencyByDate = currencyService.getCurrencyByDate(localDate.atStartOfDay());
            if (currencyByDate.isEmpty()) {
                currencyByDate = currencyService.getCurrencyByClosestDate(localDate.atStartOfDay());
            }
            if (currencyByDate.isEmpty()) {
                logger.info("No corresponding currency for date" + localDate);
                throw new NoCurrencyByDateException("No corresponding currency for date" + localDate);
            }

            for (EmployeeDTO employeeDTOto : allEmployees) {
                salaryDtos.add(new SalaryDto(null, employeeDTOto.getFullName(),
                        usdToGelConverter(employeeDTOto.getSalary(), currencyByDate.get().getRate()), currencyByDate.get().getCurrencyDate()));
            }
        }
        return salaryEntitiesToSalaryDtos(salaryRepository.saveAll(salaryDtosToSalaryEntities(salaryDtos)));
    }

    private static List<SalaryDto> salaryEntitiesToSalaryDtos(List<SalaryEntity> entities) {
        List<SalaryDto> dtos = new ArrayList<>();
        for (SalaryEntity entity : entities) {
            dtos.add(new SalaryDto(entity.getId(), entity.getEmployeeName(), entity.getSalaryGEL(), entity.getCurrencyDate()));
        }
        return dtos;
    }

    private static List<SalaryEntity> salaryDtosToSalaryEntities(List<SalaryDto> dtos) {
        List<SalaryEntity> entities = new ArrayList<>();
        for (SalaryDto salaryDto : dtos) {
            entities.add(salaryDtoToSalaryEntity(salaryDto));
        }
        return entities;
    }

    private static SalaryEntity salaryDtoToSalaryEntity(SalaryDto dto) {
        return new SalaryEntity(dto.getId(), dto.getEmployeeName(), dto.getSalaryGEL(), dto.getCurrencyDate());
    }

    private static BigDecimal usdToGelConverter(BigDecimal usdSalary, String rate) {
        return new BigDecimal(String.valueOf(usdSalary.multiply(BigDecimal.valueOf(Double.parseDouble(rate)))));
    }
}
