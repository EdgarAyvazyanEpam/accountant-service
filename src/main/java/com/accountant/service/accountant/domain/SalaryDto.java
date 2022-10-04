package com.accountant.service.accountant.domain;

import com.accountant.service.accountant.enums.SalaryEnum;
import lombok.*;

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
    private String currencyDate;
    private SalaryEnum salaryType;

}
