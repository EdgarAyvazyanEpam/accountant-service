package com.accountant.service.accountant.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EmployeeDTO {
    private long id;
    private String fullName;
    private BigDecimal salary;
    private Date creationDate;
    private String fineName;
}
