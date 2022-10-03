package com.accountant.service.accountant.service;

import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.domain.SalaryDto;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.SalaryEntity;
import com.accountant.service.accountant.enums.SalaryEnum;
import com.accountant.service.accountant.exception.salary.SalaryAlreadyCalculatedException;
import com.accountant.service.accountant.exception.currency.NoCurrencyByDateException;
import com.accountant.service.accountant.exception.handler.ApplicationExceptionHandler;
import com.accountant.service.accountant.exception.salary.SalaryNotFoundException;
import com.accountant.service.accountant.repository.SalaryRepository;
import com.accountant.service.accountant.service.interfaces.CurrencyService;
import com.accountant.service.accountant.service.interfaces.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SalaryServiceImpl implements com.accountant.service.accountant.service.interfaces.SalaryService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);
    private final EmployeeService employeeService;
    private final CurrencyService currencyService;
    private final SalaryRepository salaryRepository;

    public SalaryServiceImpl(EmployeeService employeeService, CurrencyService currencyService, SalaryRepository salaryRepository) {
        this.employeeService = employeeService;
        this.currencyService = currencyService;
        this.salaryRepository = salaryRepository;
    }

    public List<SalaryDto> calculateSalary(LocalDate localDate, SalaryEnum salaryType) {
        List<SalaryEntity> entities = new ArrayList<>();
        if (salaryType.equals(SalaryEnum.MONTHLY)) {
            entities.addAll(createMonthlySalary(localDate, salaryType));
        } else {
            for (int i = 1; i <= 12; i++) {
                entities.addAll(createMonthlySalary(localDate, salaryType));
                localDate = localDate.plusMonths(1);
            }
        }

        return salaryEntitiesToSalaryDtos(entities);
    }

    @Override
    public List<SalaryDto> getSalariesForMonth() {
        Optional<List<SalaryEntity>> salaryEntityByMonthly = salaryRepository.getSalaryEntityByMonthly();
        if (salaryEntityByMonthly.isEmpty()) {
            throw new SalaryNotFoundException("Cannot find salary for month");
        }
        return salaryEntityByMonthly.map(SalaryServiceImpl::salaryEntitiesToSalaryDtos).orElseGet(() -> salaryEntitiesToSalaryDtos(null));
    }

    @Override
    public List<SalaryDto> getSalariesForYear() {
        Optional<List<SalaryEntity>> salaryEntityByYearly = salaryRepository.getSalaryEntityByYearly();
        if (salaryEntityByYearly.isEmpty()) {
            throw new SalaryNotFoundException("Cannot find salary for year");
        }
        return salaryEntityByYearly.map(SalaryServiceImpl::salaryEntitiesToSalaryDtos).orElseGet(() -> salaryEntitiesToSalaryDtos(null));
    }

    private static List<SalaryDto> salaryEntitiesToSalaryDtos(List<SalaryEntity> entities) {
        if (entities == null) {
            throw new SalaryNotFoundException("Salary not found");
        }
        List<SalaryDto> dtos = new ArrayList<>();
        for (SalaryEntity entity : entities) {
            dtos.add(new SalaryDto(entity.getId(), entity.getEmployeeName(), entity.getSalaryGEL(), entity.getCurrencyDate(), entity.getSalaryYearlyOrMonthly()));
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
        return new SalaryEntity(dto.getId(), dto.getEmployeeName(), dto.getSalaryGEL(), dto.getCurrencyDate(), dto.getSalaryType());
    }

    private static BigDecimal usdToGelConverter(BigDecimal usdSalary, String rate) {
        return new BigDecimal(String.valueOf(usdSalary.multiply(BigDecimal.valueOf(Double.parseDouble(rate)))));
    }

    private List<SalaryEntity> createMonthlySalary(LocalDate localDate, SalaryEnum salaryType) {
        List<SalaryDto> salaryDtos = new ArrayList<>();
        if (!salaryRepository.findAll().isEmpty()) {
            if (salaryRepository.getSalaryEntityByCurrencyDate
                    (LocalDateTime.of
                            (localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0,0))
                    .isPresent()) {
                logger.info("Salary calculated for this date:" + localDate);
                throw new SalaryAlreadyCalculatedException("Salary calculated for this date:" + localDate.toString());
            } else if (currencyService.getCurrencyByClosestDate(localDate.atStartOfDay()).isPresent() && currencyService.getCurrencyByClosestDate(localDate.atStartOfDay()).get().getCurrencyDate().equals(LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0,0))) {
                logger.info("Salary calculated for this date:" + localDate);
                throw new SalaryAlreadyCalculatedException("Salary calculated for this date:" + localDate.toString());
            }
        }

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
                    usdToGelConverter(employeeDTOto.getSalary(), currencyByDate.get().getRate()), currencyByDate.get().getCurrencyDate(), salaryType));
        }
        return salaryRepository.saveAll(salaryDtosToSalaryEntities(salaryDtos));
    }
}
