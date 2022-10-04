package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVService;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.domain.EmployeeDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.entity.UploadedFileEntity;
import com.accountant.service.accountant.enums.IsoCodeEnum;
import com.accountant.service.accountant.repository.CurrencyRepository;
import com.accountant.service.accountant.repository.EmployeeRepository;
import com.accountant.service.accountant.service.helper.CurrencyHelper;
import com.accountant.service.accountant.service.helper.EmployeeHelper;
import com.accountant.service.accountant.service.interfaces.UploadedService;
import lombok.SneakyThrows;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EmployeeServiceTest {
    private EmployeeEntity employeeEntity;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private CSVService csvService;
    @Mock
    private UploadedService uploadedService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
        employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .fullName("John Smith")
                .salary(new BigDecimal(1200))
                .creationDate(LocalDateTime.now())
                .fileName("file.csv")
                .fileId("1")
                .build();
    }

    @SneakyThrows
    @Test
    public void saveCurrencyTest() {
        List<EmployeeDTO> employeeDTOList = List.of(getSampleEmployeeDto());
        MultipartFile result = new MockMultipartFile("file.csv", new byte[]{});
        List<EmployeeEntity> employeeEntities = EmployeeHelper.employeeDtosToEmployeeEntities(employeeDTOList);
        UploadedFileEntity uploadedFileEntity = new UploadedFileEntity(1L,"file.csv","file content",LocalDateTime.now());

        when(uploadedService.saveUploadedFile(result)).thenReturn(uploadedFileEntity);
        when(csvService.createEmployeeDtos(result,uploadedFileEntity)).thenReturn(employeeDTOList);
        when(employeeRepository.saveAll(any())).thenReturn(employeeEntities);
        List<EmployeeEntity> employeeEntityList = employeeService.saveEmployee(result);
        assertEquals(employeeEntityList, employeeEntities);
    }

    private EmployeeDTO getSampleEmployeeDto() {
        return new EmployeeDTO(1L,"John Smith",
                new BigDecimal(1200),LocalDateTime.now(),"file.csv","1");
    }

    @Test
    public void getAllCurrenciesTest() {
        List<EmployeeEntity> all = List.of(employeeEntity);
        when(employeeRepository.findAll()).thenReturn(all);
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees();
        assertEquals(EmployeeHelper.employeeEntitiesToEmployeeDtos(all),employeeDTOList);
    }
}
