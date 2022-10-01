package com.accountant.service.accountant.repository;

import com.accountant.service.accountant.entity.CurrencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
    @Transactional
    Long deleteByFileId(String id);

    @Transactional
    CurrencyEntity getCurrencyEntityByCurrencyDate(LocalDateTime currencyDate);

    @Query("select c from CurrencyEntity c  where currencyDate <= :endDate and currencyDate > :startDate order by currencyDate desc ")
    Page<CurrencyEntity> getCurrencyEntityByClosestDate(LocalDateTime startDate, LocalDateTime endDate, Pageable limit);
}
