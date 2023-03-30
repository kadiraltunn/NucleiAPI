package com.example.demmooo.repository;

import com.example.demmooo.model.ResultEntity;
import com.example.demmooo.model.ScannedEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends CrudRepository<ResultEntity, Long>
{
}
