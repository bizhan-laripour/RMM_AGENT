package com.submodule.service;

import com.submodule.entity.Alarm;
import com.submodule.repository.AlarmRepository;
import com.submodule.specification.AlarmFilterModel;
import com.submodule.specification.MongoQuery;
import com.submodule.specification.ResponseGenerator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final MongoQuery<Alarm> mongoQuery;

    public AlarmService(AlarmRepository alarmRepository, MongoQuery<Alarm> mongoQuery) {
        this.alarmRepository = alarmRepository;
        this.mongoQuery = mongoQuery;
    }


    public Alarm save(Alarm alarm){
        return alarmRepository.save(alarm);
    }

    public List<Alarm> findByIp(String ip){
        return alarmRepository.findByIp(ip);
    }


    public List<Alarm> findAll(){
        return alarmRepository.findAll();
    }


    public List<Alarm> findByActive(Boolean isActive){
        return alarmRepository.findByIsActive(isActive);
    }

    public List<Alarm> findByUUId(String uuid){
        return alarmRepository.findByThresholdUUID(uuid);
    }

    public ResponseGenerator<Alarm> search(AlarmFilterModel alarmFilterModel) throws IllegalAccessException {
        return mongoQuery.search(alarmFilterModel , Alarm.class , PageRequest.of(alarmFilterModel.getPageNumber() , alarmFilterModel.getPageSize()) , true);
    }
}
