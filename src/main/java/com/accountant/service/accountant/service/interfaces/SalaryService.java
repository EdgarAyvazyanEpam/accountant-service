package com.accountant.service.accountant.service.interfaces;

import com.accountant.service.accountant.domain.SalaryDto;

import java.time.LocalDate;
import java.util.List;

public interface SalaryService {
    List<SalaryDto> calculateSalary(LocalDate localDate);
}
