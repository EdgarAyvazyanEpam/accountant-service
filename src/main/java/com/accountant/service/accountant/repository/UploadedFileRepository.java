package com.accountant.service.accountant.repository;

import com.accountant.service.accountant.entity.UploadedFileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface UploadedFileRepository extends CrudRepository<UploadedFileEntity, Integer> {

    @Transactional
    Long deleteUploadedFileEntityById(Long id);
}
