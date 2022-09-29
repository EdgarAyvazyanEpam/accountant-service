package com.accountant.service.accountant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String fullName;
    private BigDecimal salary;
    private LocalDateTime creationDate;
    private String fileName;
    private String fileId;
}
