package com.accountant.service.accountant.entity;

import com.accountant.service.accountant.enums.IsoCodeEnum;
import com.accountant.service.accountant.enums.SalaryEnum;
import lombok.Builder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "salary")
@Builder
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
    @Enumerated(EnumType.STRING)
    @Column(name = "salary_type")
    private SalaryEnum salaryYearlyOrMonthly;

    public SalaryEntity() {
    }

    public SalaryEntity(Long id, String employeeName, BigDecimal salaryGEL, LocalDateTime currencyDate, SalaryEnum salaryYearlyOrMonthly) {
        this.id = id;
        this.employeeName = employeeName;
        this.salaryGEL = salaryGEL;
        this.currencyDate = currencyDate;
        this.salaryYearlyOrMonthly = salaryYearlyOrMonthly;
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

    public SalaryEnum getSalaryYearlyOrMonthly() {
        return salaryYearlyOrMonthly;
    }

    public void setSalaryYearlyOrMonthly(SalaryEnum salaryYearlyOrMonthly) {
        this.salaryYearlyOrMonthly = salaryYearlyOrMonthly;
    }
}
