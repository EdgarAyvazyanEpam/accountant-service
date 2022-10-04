package com.accountant.service.accountant.service;

import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.EmployeeEntity;
import com.accountant.service.accountant.entity.UploadedFileEntity;
import com.accountant.service.accountant.enums.IsoCodeEnum;
import com.accountant.service.accountant.repository.CurrencyRepository;
import com.accountant.service.accountant.repository.EmployeeRepository;
import com.accountant.service.accountant.repository.UploadedFileRepository;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UploadedFileServiceTest {

    private UploadedFileEntity uploadedFileEntity;
    private MultipartFile currencyFile;
    private MultipartFile employeeFIle;
    private CurrencyEntity currencyEntity;

    @Mock
    private UploadedFileRepository uploadedFileRepository;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private UploadedFileServiceImpl uploadedFileService;
    private EmployeeEntity employeeEntity;

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        Path path = Paths.get("src/main/resources/files/HistoryExchangeReport.csv");
        String name = "HistoryExchangeReport.csv";
        String originalFileName = "HistoryExchangeReport.csv";
        String contentType = "text/csv";
        byte[] content = null;
        content = Files.readAllBytes(path);

        currencyFile = new MockMultipartFile(name,
                originalFileName, contentType, content);
        path = Paths.get("src/main/resources/files/HistoryExchangeReport.csv");
        name = "HistoryExchangeReport.csv";
        originalFileName = "HistoryExchangeReport.csv";
        contentType = "text/csv";
        content = null;
        content = Files.readAllBytes(path);

        employeeFIle = new MockMultipartFile(name,
                originalFileName, contentType, content);

        uploadedFileEntity = UploadedFileEntity.builder()
                .id(1L)
                .fileName(currencyFile.getOriginalFilename())
                .fileContent(content.toString())
                .creationDate(LocalDateTime.now())
                .build();

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

        employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .fullName("John Smith")
                .salary(new BigDecimal(1200))
                .creationDate(LocalDateTime.now())
                .fileName("file.csv")
                .fileId("1")
                .build();
    }

    @Test
    public void saveUploadedFileTest() {
        when(uploadedFileRepository.save(any())).thenReturn(uploadedFileEntity);
        UploadedFileEntity uploadedFileEntity = uploadedFileService.saveUploadedFile(currencyFile);
        assertEquals(this.uploadedFileEntity,uploadedFileEntity);
    }

    @SneakyThrows
    @Test
    public void deleteCurrencyFileById() {
        when(uploadedFileRepository.deleteUploadedFileEntityById(any())).thenReturn(Optional.ofNullable(uploadedFileEntity));
        when(currencyRepository.deleteByFileId(any())).thenReturn(Optional.ofNullable(currencyEntity));
        Optional<CurrencyEntity> currencyEntity = uploadedFileService.deleteCurrencyFileById(1L);
        Optional<CurrencyEntity> currencyEntity1 = currencyRepository.deleteByFileId(currencyEntity.get().getFileId());

        Optional<UploadedFileEntity> byId = uploadedFileRepository.findById(1);
        Optional<CurrencyEntity> byId1 = currencyRepository.findById(1L);
        assertEquals(currencyEntity,currencyEntity1);
        assertTrue(byId.isEmpty());
        assertTrue(byId1.isEmpty());
    }

    @SneakyThrows
    @Test
    public void deleteEmployeeFileById() {
        when(uploadedFileRepository.deleteUploadedFileEntityById(any())).thenReturn(Optional.ofNullable(uploadedFileEntity));
        when(employeeRepository.deleteByFileId(any())).thenReturn(Optional.ofNullable(employeeEntity));
        Optional<EmployeeEntity> currencyEntity = uploadedFileService.deleteEmployeeFileById(1L);
        Optional<EmployeeEntity> currencyEntity1 = employeeRepository.deleteByFileId(currencyEntity.get().getFileId());
        Optional<UploadedFileEntity> byId = uploadedFileRepository.findById(1);
        Optional<EmployeeEntity> byId1 = employeeRepository.findById(1L);
        assertEquals(currencyEntity,currencyEntity1);
        assertTrue(byId.isEmpty());
        assertTrue(byId1.isEmpty());

    }

    @SneakyThrows
    @Test
    public void finedFileByNameTest() {
        when(uploadedFileRepository.findByFileName(any())).thenReturn(Optional.ofNullable(uploadedFileEntity));
        Optional<UploadedFileEntity> uploadedFileEntity = uploadedFileService.finedFileByName(this.uploadedFileEntity.getFileName());
        assertEquals(this.uploadedFileEntity,uploadedFileEntity.get());
    }

    @SneakyThrows
    @Test
    public void deleteFileByIdTest() {
        when(uploadedFileRepository.findByFileName(any())).thenReturn(Optional.ofNullable(uploadedFileEntity));
        Optional<UploadedFileEntity> uploadedFileEntity = uploadedFileService.deleteFileById(this.uploadedFileEntity.getId());
        assertTrue(uploadedFileEntity.isEmpty());
    }

}
