package com.example.demmooo.repository;

import com.example.demmooo.model.ResultEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends CrudRepository<ResultEntity, Long> {
}
