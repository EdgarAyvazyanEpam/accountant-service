package com.accountant.service.accountant.repository;

import com.accountant.service.accountant.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

}
