package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVService;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService implements com.accountant.service.accountant.service.interfaces.CurrencyService {
    private final CSVService csvService;
    private final UploadedFileService uploadedFileService;
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CSVService csvService, UploadedFileService uploadedFileService, CurrencyRepository currencyRepository) {
        this.csvService = csvService;
        this.uploadedFileService = uploadedFileService;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void saveCurrency(MultipartFile file) {
        List<CurrencyDTO> currencyDTOS = csvService.saveCurrency(file, uploadedFileService.saveUploadedFile(file));
        currencyRepository.saveAll(currencyDtosToCurrencyEntities(currencyDTOS));
    }

    @Override
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

        return new CurrencyEntity(dto.getId(), dto.getCurrencyDate(),
                dto.getRate(), dto.getIsoCodeFrom(), dto.getIsoCodeTo(), dto.getCreationDate(),
                dto.getFileName(), dto.getFileId());
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
        dto.setFileId(entity.getFileId());

        return dto;
    }

}
