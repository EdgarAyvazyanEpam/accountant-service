package com.accountant.service.accountant.repository;

import com.accountant.service.accountant.entity.UploadedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UploadedFileRepository extends JpaRepository<UploadedFileEntity, Integer> {

    @Transactional
    @Modifying
    @Query("delete from UploadedFileEntity f where f.fileName=:fileName")
    Integer deleteCurrencyFileByName(@Param("fileName") String fileName);

    @Transactional
    @Modifying
    @Query("delete from UploadedFileEntity e where e.fileName=:fileName")
    Integer deleteEmployeeFileByName(@Param("fileName") String fileName);
}
