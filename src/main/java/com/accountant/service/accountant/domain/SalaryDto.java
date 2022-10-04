package com.accountant.service.accountant.domain;

import com.accountant.service.accountant.enums.SalaryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SalaryDto {
    private String employeeName;
    private BigDecimal salaryGEL;
    private LocalDateTime currencyDate;
    private SalaryEnum salaryType;

}
