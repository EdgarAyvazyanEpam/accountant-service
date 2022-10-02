package com.accountant.service.accountant.service.interfaces;

import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CurrencyService {
    List<CurrencyEntity> saveCurrency(MultipartFile file);

    List<CurrencyDTO> getAllCurrencies();

    Optional<CurrencyEntity> getCurrencyByDate(LocalDateTime localDateTime);

    Optional<CurrencyEntity> getCurrencyByClosestDate(LocalDateTime localDateTime);
}
