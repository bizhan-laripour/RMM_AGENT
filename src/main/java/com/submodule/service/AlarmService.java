package com.submodule.service;

import com.submodule.entity.Alarm;
import com.submodule.repository.AlarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
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
        return alarmRepository.findByActive(isActive);
    }
}
