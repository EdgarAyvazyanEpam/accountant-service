package com.accountant.service.accountant.controller;

import com.accountant.service.accountant.csv.csvservice.csvmessage.ResponseMessage;
import com.accountant.service.accountant.domain.SalaryDto;
import com.accountant.service.accountant.enums.SalaryEnum;
import com.accountant.service.accountant.service.SalaryServiceImpl;
import com.accountant.service.accountant.service.interfaces.SalaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/calculate-monthly-salary")
    public ResponseEntity<ResponseMessage> calculateSalary(@RequestParam("localDate") String localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        salaryService.calculateSalary(LocalDate.parse(localDate, formatter), SalaryEnum.MONTHLY);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Monthly salary calculated: for date" + localDate.toString()));
    }

    @GetMapping("/monthly-salary")
    public ResponseEntity<List<SalaryDto>> getMonthlyCalculatedSalaries() {
        return new ResponseEntity<>(salaryService.getSalariesForMonth(), HttpStatus.OK);
    }
}
