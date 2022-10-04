package com.accountant.service.accountant.service;

import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.domain.SalaryDto;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.enums.SalaryEnum;
import com.accountant.service.accountant.exception.currency.NoCurrencyByDateException;
import com.accountant.service.accountant.exception.handler.ApplicationExceptionHandler;
import com.accountant.service.accountant.service.interfaces.CurrencyService;
import com.accountant.service.accountant.service.interfaces.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class SalaryServiceImpl implements com.accountant.service.accountant.service.interfaces.SalaryService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);
    private final EmployeeService employeeService;
    private final CurrencyService currencyService;

    public SalaryServiceImpl(EmployeeService employeeService, CurrencyService currencyService) {
        this.employeeService = employeeService;
        this.currencyService = currencyService;
    }

    public List<SalaryDto> calculateSalary(LocalDate localDate, SalaryEnum salaryType) {
        List<SalaryDto> salaryDtos = new ArrayList<>();
        if (salaryType.equals(SalaryEnum.MONTHLY)) {
            salaryDtos.addAll(createMonthlySalary(localDate, salaryType));
        } else {
            for (int i = 1; i <= 12; i++) {
                salaryDtos.addAll(createMonthlySalary(localDate, salaryType));
                localDate = localDate.plusMonths(1);
            }
        }

        return salaryDtos;
    }

    private static BigDecimal usdToGelConverter(BigDecimal usdSalary, String rate) {
        return new BigDecimal(String.valueOf(usdSalary.multiply(BigDecimal.valueOf(Double.parseDouble(rate)))));
    }

    private List<SalaryDto> createMonthlySalary(LocalDate localDate, SalaryEnum salaryType) {
        List<SalaryDto> salaryDtos = new ArrayList<>();
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
            salaryDtos.add(SalaryDto.builder()
                            .employeeName(employeeDTOto.getFullName())
                            .salaryGEL(usdToGelConverter(employeeDTOto.getSalary(), currencyByDate.get().getRate()))
                                    .currencyDate(currencyByDate.get().getCurrencyDate())
                                            .salaryType(salaryType)
                                                    .build());
        }
        return salaryDtos;
    }
}
