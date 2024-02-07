package com.submodule.repository;

import com.submodule.entity.Threshold;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThresholdRepository extends MongoRepository<Threshold, String > {

    List<Threshold> findAllByIpOrderByDateAsc(String ip);

}
