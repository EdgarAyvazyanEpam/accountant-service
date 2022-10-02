package com.accountant.service.accountant.service.helper;

import com.accountant.service.accountant.domain.CurrencyDTO;
import com.accountant.service.accountant.entity.CurrencyEntity;

import java.util.ArrayList;
import java.util.List;

public class CurrencyHelper {
    public static List<CurrencyEntity> currencyDtosToCurrencyEntities(List<CurrencyDTO> currencyDTOS) {
        List<CurrencyEntity> currencyEntities = new ArrayList<>();
        for (CurrencyDTO dto : currencyDTOS) {
            currencyEntities.add(currencyDtoToCurrencyEntity(dto));
        }
        return currencyEntities;
    }

    public static CurrencyEntity currencyDtoToCurrencyEntity(CurrencyDTO dto) {

        return new CurrencyEntity(dto.getId(), dto.getCurrencyDate(), dto.getCurrencyDay(),
                dto.getRate(), dto.getIsoCodeFrom(), dto.getIsoCodeTo(), dto.getCreationDate(),
                dto.getFileName(), dto.getFileId());
    }

    public static List<CurrencyDTO> currencyEntitiesToCurrencyDtos(List<CurrencyEntity> entities) {
        List<CurrencyDTO> currencyDTOS = new ArrayList<>();
        for (CurrencyEntity entity : entities) {
            currencyDTOS.add(currencyEntityToCurrencyDto(entity));
        }
        return currencyDTOS;
    }

    public static CurrencyDTO currencyEntityToCurrencyDto(CurrencyEntity entity) {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setId(entity.getId());
        dto.setCurrencyDate(entity.getCurrencyDate());
        dto.setCurrencyDay(entity.getCurrencyDay());
        dto.setRate(entity.getRate());
        dto.setIsoCodeFrom(entity.getIsoCodeFrom());
        dto.setIsoCodeTo(entity.getIsoCodeTo());
        dto.setCreationDate(entity.getCreationDate());
        dto.setFileName(entity.getFileName());
        dto.setFileId(entity.getFileId());

        return dto;
    }
}
