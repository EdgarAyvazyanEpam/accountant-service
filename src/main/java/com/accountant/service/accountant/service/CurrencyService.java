package com.accountant.service.accountant.service;

import com.accountant.service.accountant.csv.csvservice.CSVService;
import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;
import com.accountant.service.accountant.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CurrencyService {
    private final CSVService csvService;

    public CurrencyService(CSVService csvService) {
        this.csvService = csvService;
    }

    public void saveCurrency(MultipartFile file) {
        csvService.saveCurrency(file);
    }

    public List<CurrencyDTO> getAllCurrencies() {
        return csvService.getAllCurrencies();
    }
}
