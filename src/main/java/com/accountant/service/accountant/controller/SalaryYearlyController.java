package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.domain.SalaryDto;
import com.accountant.service.accountant.enums.SalaryEnum;
import com.accountant.service.accountant.service.SalaryServiceImpl;
import com.accountant.service.accountant.service.interfaces.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api")
public class SalaryYearlyController {
    private final SalaryService salaryService;

    public SalaryYearlyController(SalaryServiceImpl salaryServiceImpl) {
        this.salaryService = salaryServiceImpl;
    }

    @GetMapping("/calculate-yearly-salary")
    public ResponseEntity<List<SalaryDto>> calculateSalary() {
        LocalDate startOfTheYear = LocalDate.of(LocalDate.now().getYear(),1,7);
        return new ResponseEntity<>(salaryService.calculateSalary(startOfTheYear, SalaryEnum.YEARLY), HttpStatus.OK);
    }
}
