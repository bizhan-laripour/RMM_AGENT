package com.submodule.scheduler;

import com.google.gson.Gson;
import com.submodule.conf.RabbitConf;
import com.submodule.entity.Alarm;
import com.submodule.dto.ZabbixResponseDto;
import com.submodule.dto.ZabbixResultItemDto;
import com.submodule.entity.Threshold;
import com.submodule.enums.Category;
import com.submodule.feign.WorkerFeign;
import com.submodule.service.AlarmService;
import com.submodule.service.ThresholdService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class WorkerScheduler {

    private final Environment environment;

    private final RabbitTemplate rabbitTemplate;

    private final WorkerFeign workerFeign;

    private final ThresholdService thresholdService;

    private final AlarmService alarmService;


    private static Map<String , String> ipRanges = new HashMap<>();

    public WorkerScheduler(Environment environment, RabbitTemplate rabbitTemplate, WorkerFeign workerFeign, ThresholdService thresholdService, AlarmService alarmService) {
        this.environment = environment;
        this.rabbitTemplate = rabbitTemplate;
        this.workerFeign = workerFeign;
        this.thresholdService = thresholdService;
        this.alarmService = alarmService;
    }

    @Scheduled(fixedRate = 60000)
    public void getDeviceInformationFromElasticSearch() throws Exception {

        List<Threshold> thresholds = thresholdService.findByIp(environment.getProperty("agent.ip"));
        if (!thresholds.isEmpty()) {
            ZabbixResponseDto zabbixResponseDto = workerFeign.getFromWorker(environment.getProperty("agent.ip"));
            List<Alarm> alarms = new ArrayList<>();
            for (Threshold threshold : thresholds) {
                Alarm alarm = compareAndCheck(zabbixResponseDto, threshold);
                if (alarm != null) {
                    alarms.add(alarm);
                    alarmService.save(alarm);
                }
            }
            Gson gson = new Gson();
            String obj = gson.toJson(alarms);
            rabbitTemplate.convertAndSend(RabbitConf.topicExchangeName, "foo.bar.baz", obj);
        }
    }


    public void populateIpTable(){

    }
    /**
     * the scheduler checks active alarms
     */
    @Scheduled(fixedRate = 60000)
    public void checkAlarms() {
        List<Threshold> thresholds = thresholdService.findByIp(environment.getProperty("agent.ip"));
        List<Alarm> alarms = alarmService.findByIp(environment.getProperty("agent.ip"));
        ZabbixResponseDto zabbixResponseDto = workerFeign.getFromWorker(environment.getProperty("agent.ip"));
        alarms.stream().filter(Alarm::getActive).forEach(alarm ->checkActiveAlarms(alarm , thresholds , zabbixResponseDto));
    }

    private void checkActiveAlarms(Alarm alarm, List<Threshold> thresholds, ZabbixResponseDto zabbixResponseDto) {
        if (alarm.getCategory().equals(Category.MEMORY_USAGE)) {
            List<Threshold> t = thresholds.stream().filter(threshold -> threshold.getCategory().equals(Category.MEMORY_USAGE)).toList();
            List<ZabbixResultItemDto> memory = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37412")).toList();
            if (t.get(0).getPercentage() > Double.parseDouble(memory.get(0).getLastvalue())) {
                alarm.setActive(false);
                alarmService.save(alarm);
            }
        }else if(alarm.getCategory().equals(Category.CPU_LOAD)){
            List<Threshold> t = thresholds.stream().filter(threshold -> threshold.getCategory().equals(Category.CPU_LOAD)).toList();
            List<ZabbixResultItemDto> cpu = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37415")).toList();
            if (t.get(0).getPercentage() > Double.parseDouble(cpu.get(0).getLastvalue())) {
                alarm.setActive(false);
                alarmService.save(alarm);
            }
        }else if(alarm.getCategory().equals(Category.HARD_DISK_USAGE)){
            List<Threshold> t = thresholds.stream().filter(threshold -> threshold.getCategory().equals(Category.HARD_DISK_USAGE)).toList();
            List<ZabbixResultItemDto> hard = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37608")).toList();
            if (t.get(0).getPercentage() > Double.parseDouble(hard.get(0).getLastvalue())) {
                alarm.setActive(false);
                alarmService.save(alarm);
            }
        }
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
            if (threshold.getPercentage() < Double.parseDouble(memory.get(0).getLastvalue())) {
                Alarm alarm = new Alarm();
                alarm.setCategory(threshold.getCategory());
                //this is available memory
                alarm.setMemoryUsage(Double.parseDouble(zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37411")).toList().get(0).getLastvalue()));
                return alarm;
            }
        }
        return null;
    }

    private Alarm checkHard(ZabbixResponseDto zabbixResponseDto, Threshold threshold) throws Exception {
        List<ZabbixResultItemDto> spaceUtilization = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37608")).toList();
        if (!spaceUtilization.isEmpty()) {
            if (threshold.getPercentage() < Double.parseDouble(spaceUtilization.get(0).getLastvalue())) {
                Alarm alarm = new Alarm();
                alarm.setCategory(threshold.getCategory());
                alarm.setMemoryUsage(Double.parseDouble(spaceUtilization.get(0).getLastvalue()));
                return alarm;
            }
        }
        return null;
    }

    private Alarm checkCpu(ZabbixResponseDto zabbixResponseDto, Threshold threshold) {
        List<ZabbixResultItemDto> cpu = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37415")).toList();
        if (!cpu.isEmpty()) {
            if (threshold.getPercentage() < Double.parseDouble(cpu.get(0).getLastvalue())) {
                Alarm alarm = new Alarm();
                alarm.setCategory(threshold.getCategory());
                alarm.setCpuLoad(Double.parseDouble(cpu.get(0).getLastvalue()));
                return alarm;
            }
        }
        return null;
    }

}
