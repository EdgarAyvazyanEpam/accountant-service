package com.accountant.service.accountant.entity;

import com.accountant.service.accountant.enums.IsoCodeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency")
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
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
    private LocalDateTime creationDate;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_id")
    private String fileId;

    public CurrencyEntity() {
    }

    public CurrencyEntity(Long id, String currencyDate, String rate, IsoCodeEnum isoCodeFrom,
                          IsoCodeEnum isoCodeTo, LocalDateTime creationDate, String fileName, String fileId) {
        this.id = id;
        this.currencyDate = currencyDate;
        this.rate = rate;
        this.isoCodeFrom = isoCodeFrom;
        this.isoCodeTo = isoCodeTo;
        this.creationDate = creationDate;
        this.fileName = fileName;
        this.fileId = fileId;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
