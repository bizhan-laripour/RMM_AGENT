package com.submodule.scheduler;

import com.google.gson.Gson;
import com.submodule.conf.RabbitConf;
import com.submodule.dto.Alarm;
import com.submodule.dto.ZabbixResponseDto;
import com.submodule.dto.ZabbixResultItemDto;
import com.submodule.entity.Threshold;
import com.submodule.feign.WorkerFeign;
import com.submodule.service.ThresholdService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class WorkerScheduler {

    private final Environment environment;

    private final RabbitTemplate rabbitTemplate;

    private final WorkerFeign workerFeign;

    private final ThresholdService thresholdService;

    public WorkerScheduler(Environment environment, RabbitTemplate rabbitTemplate, WorkerFeign workerFeign, ThresholdService thresholdService) {
        this.environment = environment;
        this.rabbitTemplate = rabbitTemplate;
        this.workerFeign = workerFeign;
        this.thresholdService = thresholdService;
    }

    @Scheduled(fixedRate = 60000)
    public void getDeviceInformationFromElasticSearch() throws Exception {
        ZabbixResponseDto zabbixResponseDto = workerFeign.getFromWorker(environment.getProperty("agent.ip"));
        List<Threshold> thresholds = thresholdService.findByIp(environment.getProperty("agent.ip"));
        List<Alarm> alarms = new ArrayList<>();
        for (Threshold threshold : thresholds) {
            Alarm alarm = compareAndCheck(zabbixResponseDto, threshold);
            if (alarm != null) {
                alarms.add(alarm);
            }
        }
        Gson gson = new Gson();
        String obj = gson.toJson(alarms);
        rabbitTemplate.convertAndSend(RabbitConf.topicExchangeName, "foo.bar.baz", obj);

    }


    public Alarm compareAndCheck(ZabbixResponseDto zabbixResponseDto, Threshold threshold) throws Exception {
        switch (threshold.getCategory()) {
            case MEMORY_USAGE -> {
                return checkMemory(zabbixResponseDto, threshold);
            }
            case HARD_DISK_USAGE -> {
                return checkHard(zabbixResponseDto, threshold);
            }
            case CPU_LOAD -> {
                return checkCpu(zabbixResponseDto, threshold);
            }
        }
        return null;
    }

    private Alarm checkMemory(ZabbixResponseDto zabbixResponseDto, Threshold threshold) throws Exception {
        List<ZabbixResultItemDto> memory = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37412")).toList();
        if (!memory.isEmpty()) {
            if (threshold.getPercentage() < Long.valueOf(memory.get(0).getLastvalue())) {
                Alarm alarm = new Alarm();
                alarm.setCategory(threshold.getCategory());
                //this is available memory
                alarm.setMemoryUsage(Long.parseLong(zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37411")).toList().get(0).getLastvalue()));
                return alarm;
            }
        }
        return null;
    }

    private Alarm checkHard(ZabbixResponseDto zabbixResponseDto, Threshold threshold) throws Exception {
        List<ZabbixResultItemDto> spaceUtilization = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37412")).toList();
        if (!spaceUtilization.isEmpty()) {
            if (threshold.getPercentage() < Long.parseLong(spaceUtilization.get(0).getLastvalue())) {
                Alarm alarm = new Alarm();
                alarm.setCategory(threshold.getCategory());
                return alarm;
            }
        }
        return null;
    }

    private Alarm checkCpu(ZabbixResponseDto zabbixResponseDto, Threshold threshold) {
        List<ZabbixResultItemDto> cpu = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37415")).toList();
        if (!cpu.isEmpty()) {
            if (threshold.getPercentage() < Long.parseLong(cpu.get(0).getLastvalue())) {
                Alarm alarm = new Alarm();
                alarm.setCategory(threshold.getCategory());
                alarm.setCpuLoad(Long.parseLong(cpu.get(0).getLastvalue()));
                return alarm;
            }
        }
        return null;
    }

}
