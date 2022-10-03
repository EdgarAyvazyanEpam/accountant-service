package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.domain.SalaryDto;
import com.accountant.service.accountant.service.SalaryServiceImpl;
import com.accountant.service.accountant.service.interfaces.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryServiceImpl salaryServiceImpl) {
        this.salaryService = salaryServiceImpl;
    }

    @GetMapping("/calculate-salary")
    public ResponseEntity<List<SalaryDto>> calculateSalary(@RequestParam("localDate") String localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new ResponseEntity<>(salaryService.calculateSalary(LocalDate.parse(localDate, formatter)), HttpStatus.OK);
    }
}
