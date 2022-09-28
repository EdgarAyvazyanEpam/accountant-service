package com.accountant.service.accountant.domain;

import com.accountant.service.accountant.enums.IsoCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {
    private Long id;
    private String currencyDate;
    private String rate;
    private IsoCodeEnum isoCodeFrom;
    private IsoCodeEnum isoCodeTo;
    private Date creationDate;
    private String fileName;
    private String fileId;
}
