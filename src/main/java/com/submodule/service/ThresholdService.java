package com.submodule.service;

import com.submodule.entity.Threshold;
import com.submodule.repository.ThresholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ThresholdService {

    private final ThresholdRepository thresholdRepository;

    @Autowired
    public ThresholdService(ThresholdRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    public Threshold save(Threshold threshold){
        threshold.setThresholdUUID(UUID.randomUUID().toString());
        threshold.setDate(new Date());
        return thresholdRepository.save(threshold);
    }

    public List<Threshold> findAll(){
        return thresholdRepository.findAll();
    }


    public List<Threshold> findByIp(String ip){
        return thresholdRepository.findAllByIpOrderByDateAsc(ip);
    }

}
