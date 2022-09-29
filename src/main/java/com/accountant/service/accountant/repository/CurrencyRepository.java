package com.accountant.service.accountant.repository;

import com.accountant.service.accountant.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
    @Transactional
    Long deleteByFileId(String id);
}
