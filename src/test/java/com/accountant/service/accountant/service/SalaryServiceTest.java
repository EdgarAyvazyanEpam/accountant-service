package com.accountant.service.accountant.service;

import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.domain.SalaryDto;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.enums.IsoCodeEnum;
import com.accountant.service.accountant.enums.SalaryEnum;
import com.accountant.service.accountant.service.helper.EmployeeHelper;
import com.accountant.service.accountant.service.interfaces.CurrencyService;
import com.accountant.service.accountant.service.interfaces.EmployeeService;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SalaryServiceTest {
    private EmployeeDTO employeeDTO;
    private CurrencyEntity currencyEntity;

    @Mock
    private EmployeeService employeeService;
    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private SalaryServiceImpl salaryService;

    @BeforeEach
    public void setUp() {
        employeeDTO = new EmployeeDTO(1L,"John Smith",new BigDecimal(1200),String.valueOf(LocalDateTime.now()),"File.csv","1");
        currencyEntity = CurrencyEntity.builder()
                .id(1L)
                .currencyDate(LocalDateTime.of(2022, 5, 7, 0, 0))
                .currencyDay("Monday")
                .rate("2.99")
                .isoCodeFrom(IsoCodeEnum.USD)
                .isoCodeTo(IsoCodeEnum.GEL)
                .creationDate(LocalDateTime.now())
                .fileName("CurrencyFile.csv")
                .fileId("1")
                .build();
    }

    @Test
    public void calculateMonthlySalaryTest() {
        List<EmployeeDTO> all = List.of(employeeDTO);
        List<SalaryDto> salaryDtos = List.of(new SalaryDto("John Smith", new BigDecimal(1200).multiply(new BigDecimal(currencyEntity.getRate())), LocalDateTime.of(2022,5,7,0,0).toString(),SalaryEnum.MONTHLY));
        when(employeeService.getAllEmployees()).thenReturn(all);
        when(currencyService.getCurrencyByDate(any())).thenReturn(Optional.ofNullable(currencyEntity));
        List<SalaryDto> calculateSalary = salaryService.calculateSalary(LocalDate.parse(employeeDTO.getCreationDate()), SalaryEnum.MONTHLY);
        assertEquals(calculateSalary,salaryDtos);

    }

    @Test
    public void calculateYearlySalaryTest() {
        List<EmployeeDTO> all = List.of(employeeDTO);
        List<SalaryDto> salaryDtos = List.of(new SalaryDto("John Smith", new BigDecimal(1200).multiply(new BigDecimal(currencyEntity.getRate())), LocalDateTime.of(2022,5,7,0,0).toString(),SalaryEnum.YEARLY));
        when(employeeService.getAllEmployees()).thenReturn(all);
        when(currencyService.getCurrencyByDate(any())).thenReturn(Optional.ofNullable(currencyEntity));
        List<SalaryDto> calculateSalary = salaryService.calculateSalary(LocalDate.parse(employeeDTO.getCreationDate()), SalaryEnum.YEARLY);
        assertEquals(calculateSalary.get(0),salaryDtos.get(0));

    }
}
