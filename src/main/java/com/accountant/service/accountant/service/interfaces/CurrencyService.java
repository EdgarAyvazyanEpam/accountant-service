package com.accountant.service.accountant.service.interfaces;

import com.accountant.service.accountant.domain.CurrencyDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CurrencyService {
    void saveCurrency(MultipartFile file);
    List<CurrencyDTO> getAllCurrencies();
}
