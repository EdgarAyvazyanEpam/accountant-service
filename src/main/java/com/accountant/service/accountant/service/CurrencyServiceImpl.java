package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVService;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.entity.UploadedFileEntity;
import com.accountant.service.accountant.exception.currency.CSVCurrencyFileParseException;
import com.accountant.service.accountant.exception.currency.CSVCurrencyFileStoreException;
import com.accountant.service.accountant.exception.currency.CurrencyNotFoundException;
import com.accountant.service.accountant.repository.CurrencyRepository;
import com.accountant.service.accountant.service.helper.CurrencyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements com.accountant.service.accountant.service.interfaces.CurrencyService {
    private final CSVService csvService;
    private final UploadedFileServiceImpl uploadedFileServiceImpl;
    private final CurrencyRepository currencyRepository;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    public CurrencyServiceImpl(CSVService csvService, UploadedFileServiceImpl uploadedFileServiceImpl, CurrencyRepository currencyRepository) {
        this.csvService = csvService;
        this.uploadedFileServiceImpl = uploadedFileServiceImpl;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public  List<CurrencyEntity> saveCurrency(MultipartFile file) {
        List<CurrencyEntity> currencyEntities;
        try {
            List<CurrencyDTO> currencyDTOS;
            UploadedFileEntity entity = uploadedFileServiceImpl.saveUploadedFile(file);
            currencyDTOS = csvService.createCurrencyDtos(file, entity);
            currencyEntities = currencyRepository.saveAll(CurrencyHelper.currencyDtosToCurrencyEntities(currencyDTOS));
        } catch (CSVCurrencyFileParseException e) {
            String message = "Fail to store CSV file:";
            logger.error(message, e);
            throw new CSVCurrencyFileStoreException(message);
        }
        return currencyEntities;
    }

    @Override
    public Optional<CurrencyEntity> getCurrencyByDate(LocalDateTime localDateTime) {
        return currencyRepository.getCurrencyEntityByCurrencyDate(localDateTime);
    }

    @Override
    public Optional<CurrencyEntity> getCurrencyByClosestDate(LocalDateTime localDateTime) {

        Month month = localDateTime.getMonth();
        int year = localDateTime.getYear();
        LocalDateTime startOfMonth = LocalDateTime.of(year, month.getValue(), 1, 0, 0);
        LocalDateTime salaryPayDate = LocalDateTime.of(year, month.getValue(), 7, 0, 0);
        Pageable paging = PageRequest.of(0, 1);
        Page<CurrencyEntity> dd = currencyRepository.getCurrencyEntityByClosestDate(startOfMonth, salaryPayDate, paging);
        if (!dd.isEmpty() && !dd.getContent().isEmpty()) {
            return Optional.ofNullable(dd.getContent().get(0));
        }
        return Optional.empty();
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        List<CurrencyEntity> all = currencyRepository.findAll();
        if (all.isEmpty()) {
            throw new CurrencyNotFoundException("No currency data");
        } else {
            return CurrencyHelper.currencyEntitiesToCurrencyDtos(all);
        }
    }

    public CurrencyEntity getCurrencyById(Long id) {
        return currencyRepository.getById(id);
    }
}
