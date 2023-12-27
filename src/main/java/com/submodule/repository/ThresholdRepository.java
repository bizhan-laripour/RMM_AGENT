package com.submodule.repository;

import com.submodule.entity.Threshold;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThresholdRepository extends MongoRepository<Threshold, String > {

}
