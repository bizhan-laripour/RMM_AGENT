package com.submodule.repository;

import com.submodule.entity.Alarm;
import com.submodule.entity.Threshold;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlarmRepository extends MongoRepository<Alarm, String > {

    List<Alarm> findByIp(String ip);
}
