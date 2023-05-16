package com.example.demmooo.repository;

import com.example.demmooo.model.ScannedEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScannedRepository extends CrudRepository<ScannedEntity, Long> {

    List<ScannedEntity> findByScanEntityId(Long scanId);

    boolean existsByResultEntityIdAndScanEntityId(long resultEntityId, Long scanEntityId);

}
