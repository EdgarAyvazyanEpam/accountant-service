package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVService;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.UploadedFileEntity;
import com.accountant.service.accountant.enums.IsoCodeEnum;
import com.accountant.service.accountant.repository.CurrencyRepository;
import com.accountant.service.accountant.service.helper.CurrencyHelper;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CurrencyServiceImplTest {

    private CurrencyEntity currencyEntity;

    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private CSVService csvService;

    @Mock
    private UploadedService uploadedService;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    public void setUp() {
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

    @SneakyThrows
    @Test
    public void saveCurrencyTest() {
        List<CurrencyDTO> currencyDTOList = List.of(getSampleCurrencyDto());
        MultipartFile result = new MockMultipartFile("file.csv", new byte[]{});
        List<CurrencyEntity> currencyEntityList = CurrencyHelper.dtosToEntities(currencyDTOList);
        UploadedFileEntity uploadedFileEntity = new UploadedFileEntity(1L,"file.csv","file content",LocalDateTime.now());

        when(currencyRepository.saveAll(any())).thenReturn(currencyEntityList);
        when(uploadedService.saveUploadedFile(result)).thenReturn(uploadedFileEntity);
        when(csvService.createCurrencyDtos(result,uploadedFileEntity)).thenReturn(currencyDTOList);
        List<CurrencyEntity> currencyEntities = currencyService.saveCurrency(result);
        assertEquals(currencyEntities, currencyEntityList);
    }

    private CurrencyDTO getSampleCurrencyDto() {
        return new CurrencyDTO(1L, String.valueOf(LocalDateTime.now()), "Monday", "2.945", IsoCodeEnum.USD,
                IsoCodeEnum.GEL, String.valueOf(LocalDateTime.now()), "HistoryExchangeReport.csv", "1");
    }

    @Test
    public void getCurrencyByDateTest() {

        LocalDateTime localDateTime = LocalDateTime.of(2022, 5, 5, 0, 0);
        when(currencyRepository.getCurrencyEntityByCurrencyDate(localDateTime)).thenReturn(Optional.ofNullable(currencyEntity));
        Optional<CurrencyEntity> currencyByDate = currencyService.getCurrencyByDate(localDateTime);
        assertEquals(currencyByDate.get(), currencyEntity);

    }

    @Test
    public void getCurrencyByClosestDateTest() {
        PageImpl<CurrencyEntity> currencyEntities = new PageImpl<>(List.of(currencyEntity));
        when(currencyRepository.getCurrencyEntityByClosestDate(any(), any(), any())).thenReturn(currencyEntities);
        Optional<CurrencyEntity> currencyByClosestDate = currencyService.getCurrencyByClosestDate(LocalDateTime.now());
        assertEquals(currencyByClosestDate.get(), currencyEntity);
    }

    @Test
    public void getAllCurrenciesTest() {
        List<CurrencyEntity> all = List.of(new CurrencyEntity(1L,
                LocalDateTime.now(),"monday","2.22",IsoCodeEnum.USD,IsoCodeEnum.GEL,LocalDateTime.now(),"file.csv","1"));
        when(currencyRepository.findAll()).thenReturn(all);
        List<CurrencyDTO> currencies = currencyService.getAllCurrencies();
        assertEquals(CurrencyHelper.currencyEntitiesToCurrencyDtos(all),currencies);
    }
}
