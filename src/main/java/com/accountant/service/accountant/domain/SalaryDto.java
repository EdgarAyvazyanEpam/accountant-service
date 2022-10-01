package com.accountant.service.accountant.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

public class SalaryDto implements Comparable<SalaryDto> {
    private Long id;
    private String employeeName;
    private BigDecimal salaryGEL;
    private LocalDateTime currencyDate;

    public SalaryDto() {
    }

    public SalaryDto(Long id, String employeeName, BigDecimal salaryGEL, LocalDateTime currencyDate) {
        this.id = id;
        this.employeeName = employeeName;
        this.salaryGEL = salaryGEL;
        this.currencyDate = currencyDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public BigDecimal getSalaryGEL() {
        return salaryGEL;
    }

    public void setSalaryGEL(BigDecimal salaryGEL) {
        this.salaryGEL = salaryGEL;
    }

    public LocalDateTime getCurrencyDate() {
        return currencyDate;
    }

    public void setCurrencyDate(LocalDateTime currencyDate) {
        this.currencyDate = currencyDate;
    }

    @Override
    public int compareTo(SalaryDto o) {
        return Comparator
                .comparing(SalaryDto::getEmployeeName, String::compareTo)
                .thenComparing(SalaryDto::getCurrencyDate)
                .compare(this, o);
    }
}
