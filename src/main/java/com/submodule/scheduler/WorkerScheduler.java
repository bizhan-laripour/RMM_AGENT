package com.submodule.scheduler;

import com.submodule.dto.ZabbixResponseDto;
import com.submodule.dto.ZabbixResultItemDto;
import com.submodule.entity.Threshold;
import com.submodule.feign.WorkerFeign;
import com.submodule.service.ThresholdService;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class WorkerScheduler {

    private final Environment environment;

    private final WorkerFeign workerFeign;

    private final ThresholdService thresholdService;

    public WorkerScheduler(Environment environment, WorkerFeign workerFeign, ThresholdService thresholdService) {
        this.environment = environment;
        this.workerFeign = workerFeign;
        this.thresholdService = thresholdService;
    }

    @Scheduled(fixedRate = 60000)
    public void getDeviceInformationFromElasticSearch() throws Exception {
        ZabbixResponseDto zabbixResponseDto = workerFeign.getFromWorker(environment.getProperty("agent.ip"));
        List<Threshold> thresholds = thresholdService.findByIp(environment.getProperty("agent.ip"));
        for(Threshold threshold: thresholds){
            compareAndCheck(zabbixResponseDto , threshold);
        }

    }


    public void compareAndCheck(ZabbixResponseDto zabbixResponseDto , Threshold threshold) throws Exception {
        switch (threshold.getCategory()){
            case MEMORY_USAGE : checkMemory(zabbixResponseDto , threshold);
        }
    }

    private void checkMemory(ZabbixResponseDto zabbixResponseDto , Threshold threshold) throws Exception {
        List<ZabbixResultItemDto> result = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37412")).toList();
        if(!result.isEmpty()){
            if(threshold.getPercentage() < Long.valueOf(result.get(0).getLastvalue())){
                throw new Exception("memory is under attack");
            }
        }
    }
}
