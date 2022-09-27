package com.accountant.service.accountant.domain;

import com.accountant.service.accountant.enums.IsoCodeEnum;
import lombok.Data;

import java.util.Date;

@Data
public class Currency {
    private long id;
    private Date dateDate;
    private double rate;
    private IsoCodeEnum currency;
    private Date creationDate;
    private String fineName;

}
