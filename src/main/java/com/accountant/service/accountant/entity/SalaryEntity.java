package com.accountant.service.accountant.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "salary")
public class SalaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "employee_name")
    private String employeeName;
    @Column(name = "salary_gel")
    private BigDecimal salaryGEL;
    @Column(name = "currency_date")
    private LocalDateTime currencyDate;

    public SalaryEntity() {
    }

    public SalaryEntity(Long id, String employeeName, BigDecimal salaryGEL, LocalDateTime currencyDate) {
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

}
