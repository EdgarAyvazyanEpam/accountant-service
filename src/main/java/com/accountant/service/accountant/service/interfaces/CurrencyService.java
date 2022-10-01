package com.accountant.service.accountant.service.interfaces;

import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface CurrencyService {
    void saveCurrency(MultipartFile file);

    List<CurrencyDTO> getAllCurrencies();

    CurrencyEntity getCurrencyByDate(LocalDateTime localDateTime);

    CurrencyEntity getCurrencyByClosestDate(LocalDateTime localDateTime);
}
