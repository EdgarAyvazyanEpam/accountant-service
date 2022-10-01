package com.accountant.service.accountant.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "salary")
    private BigDecimal salary;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_id")
    private String fileId;

    public EmployeeEntity() {
    }

    public EmployeeEntity(Long id, String fullName, BigDecimal salary,
                          LocalDateTime creationDate, String fileName, String fileId) {
        this.id = id;
        this.fullName = fullName;
        this.salary = salary;
        this.creationDate = creationDate;
        this.fileName = fileName;
        this.fileId = fileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}


