package com.accountant.service.accountant.entity;

import com.accountant.service.accountant.enums.IsoCodeEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "currency")
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "currency_date")
    private String currencyDate;

    @Column(name = "rate")
    private String rate;

    @Enumerated(EnumType.STRING)
    @Column(name = "iso_code_from")
    private IsoCodeEnum isoCodeFrom;
    @Enumerated(EnumType.STRING)

    @Column(name = "iso_code_to")
    private IsoCodeEnum isoCodeTo;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "file_name")
    private String fileName;

    public CurrencyEntity() {
    }

    public CurrencyEntity(Long id, String currencyDate, String rate, IsoCodeEnum isoCodeFrom,
                          IsoCodeEnum isoCodeTo, Date creationDate, String fileName) {
        this.id = id;
        this.currencyDate = currencyDate;
        this.rate = rate;
        this.isoCodeFrom = isoCodeFrom;
        this.isoCodeTo = isoCodeTo;
        this.creationDate = creationDate;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyDate() {
        return currencyDate;
    }

    public void setCurrencyDate(String currencyDate) {
        this.currencyDate = currencyDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public IsoCodeEnum getIsoCodeFrom() {
        return isoCodeFrom;
    }

    public void setIsoCodeFrom(IsoCodeEnum isoCodeFrom) {
        this.isoCodeFrom = isoCodeFrom;
    }

    public IsoCodeEnum getIsoCodeTo() {
        return isoCodeTo;
    }

    public void setIsoCodeTo(IsoCodeEnum isoCodeTo) {
        this.isoCodeTo = isoCodeTo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
