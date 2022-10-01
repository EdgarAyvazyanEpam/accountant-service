package com.accountant.service.accountant.repository;

import com.accountant.service.accountant.entity.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryEntity, Long> {
    Optional<SalaryEntity> getSalaryEntityByCurrencyDate(LocalDateTime localDateTime);
}
