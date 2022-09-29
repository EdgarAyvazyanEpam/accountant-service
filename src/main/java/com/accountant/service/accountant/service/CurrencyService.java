package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVService;
import com.accountant.service.accountant.csv.helper.CSVCurrencyHelper;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.exception.CSVCurrencyFileParseException;
import com.accountant.service.accountant.exception.CSVCurrencyFileStoreException;
import com.accountant.service.accountant.exception.CurrencyNotFoundException;
import com.accountant.service.accountant.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService implements com.accountant.service.accountant.service.interfaces.CurrencyService {
    private final CSVService csvService;
    private final UploadedFileService uploadedFileService;
    private final CurrencyRepository currencyRepository;

    private static final Logger logger = LoggerFactory.getLogger(CSVCurrencyHelper.class);

    public CurrencyService(CSVService csvService, UploadedFileService uploadedFileService, CurrencyRepository currencyRepository) {
        this.csvService = csvService;
        this.uploadedFileService = uploadedFileService;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void saveCurrency(MultipartFile file) {
        List<CurrencyDTO> currencyDTOS = null;
        try {
            currencyDTOS = csvService.createCurrencyDtos(file, uploadedFileService.saveUploadedFile(file));
            currencyRepository.saveAll(currencyDtosToCurrencyEntities(currencyDTOS));
        } catch (CSVCurrencyFileParseException e) {
            String message = "Fail to store CSV file:";
            logger.error(message, e);
            throw new CSVCurrencyFileStoreException(message);
        }
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        List<CurrencyEntity> all = currencyRepository.findAll();
        if (all.isEmpty()) {
            throw new CurrencyNotFoundException("No currency data");
        }else {
            return currencyEntitiesToCurrencyDtos(all);
        }
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
