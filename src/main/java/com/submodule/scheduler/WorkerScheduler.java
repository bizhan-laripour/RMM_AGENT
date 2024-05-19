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
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@Component
public class WorkerScheduler {

    private final Environment environment;

    private final RabbitTemplate rabbitTemplate;

    private final WorkerFeign workerFeign;

    private final ThresholdService thresholdService;

    private final AlarmService alarmService;


    public WorkerScheduler(Environment environment, RabbitTemplate rabbitTemplate, WorkerFeign workerFeign,
                           ThresholdService thresholdService, AlarmService alarmService) {
        this.environment = environment;
        this.rabbitTemplate = rabbitTemplate;
        this.workerFeign = workerFeign;
        this.thresholdService = thresholdService;
        this.alarmService = alarmService;
    }

    @Scheduled(fixedRate = 60000)
    public void getDeviceInformationFromElasticSearch() {

        List<Threshold> thresholds = thresholdService.findByIp(environment.getProperty("agent.ip"));
        if (!thresholds.isEmpty()) {
            ZabbixResponseDto zabbixResponseDto = workerFeign.getFromWorker(environment.getProperty("agent.ip"));
            List<ZabbixResponseDto> zabbixResponseDtoList = workerFeign.getAllFromWorker(environment.getProperty("agent.ip"));
            List<Alarm> alarms = new ArrayList<>();
            for (Threshold threshold : thresholds) {
                Alarm alarm = compareAndCheck(zabbixResponseDto, zabbixResponseDtoList, threshold);
                if (alarm != null) {
                    alarms.add(alarm);
                    alarmService.save(alarm);
                }
            }
            Gson gson = new Gson();
            String obj = gson.toJson(alarms);
            checkAlarms();
            rabbitTemplate.convertAndSend(RabbitConf.exchange, RabbitConf.routingAlarmKey, obj);
        }
    }


    /**
     * the scheduler checks active alarms
     */

    public void checkAlarms() {
        List<Threshold> thresholds = thresholdService.findByIp(environment.getProperty("agent.ip"));
        List<Alarm> alarms = alarmService.findByIp(environment.getProperty("agent.ip"));
        ZabbixResponseDto zabbixResponseDto = workerFeign.getFromWorker(environment.getProperty("agent.ip"));
        alarms.stream().filter(Alarm::getActive).forEach(alarm -> checkActiveAlarms(alarm, thresholds, zabbixResponseDto));
    }

    private void checkActiveAlarms(Alarm alarm, List<Threshold> thresholds, ZabbixResponseDto zabbixResponseDto) {
        if (alarm.getCategory().equals(Category.MEMORY_USAGE)) {
            List<Threshold> t = thresholds.stream().filter(threshold -> threshold.getCategory().equals(Category.MEMORY_USAGE)).toList();
            List<ZabbixResultItemDto> memory = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37412")).toList();
            if (t.get(0).getPercentage() >= Double.parseDouble(memory.get(0).getLastvalue())) {
                alarm.setActive(false);
                alarmService.save(alarm);
            }
        } else if (alarm.getCategory().equals(Category.CPU_LOAD)) {
            List<Threshold> t = thresholds.stream().filter(threshold -> threshold.getCategory().equals(Category.CPU_LOAD)).toList();
            List<ZabbixResultItemDto> cpu = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37415")).toList();
            if (t.get(0).getPercentage() >= Double.parseDouble(cpu.get(0).getLastvalue())) {
                alarm.setActive(false);
                alarmService.save(alarm);
            }
        } else if (alarm.getCategory().equals(Category.HARD_DISK_USAGE)) {
            List<Threshold> t = thresholds.stream().filter(threshold -> threshold.getCategory().equals(Category.HARD_DISK_USAGE)).toList();
            List<ZabbixResultItemDto> hard = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37608")).toList();
            if (t.get(0).getPercentage() >= Double.parseDouble(hard.get(0).getLastvalue())) {
                alarm.setActive(false);
                alarmService.save(alarm);
            }
        }
    }


    public Alarm compareAndCheck(ZabbixResponseDto zabbixResponseDto, List<ZabbixResponseDto> zabbixResponseDtoList, Threshold threshold) {
        switch (threshold.getCategory()) {
            case MEMORY_USAGE -> {
                return checkMemory(zabbixResponseDto, zabbixResponseDtoList, threshold);
            }
            case HARD_DISK_USAGE -> {
                return checkHard(zabbixResponseDto, zabbixResponseDtoList, threshold);
            }
            case CPU_LOAD -> {
                return checkCpu(zabbixResponseDto, zabbixResponseDtoList, threshold);
            }
        }
        return null;
    }

    private Alarm checkMemory(ZabbixResponseDto zabbixResponseDto, List<ZabbixResponseDto> zabbixResponseDtoList, Threshold threshold) {
        List<ZabbixResultItemDto> memory = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37412")).toList();
        if (!memory.isEmpty()) {
            if (threshold.getPercentage() <= Double.parseDouble(memory.get(0).getLastvalue())) {
                List checkTime = checkTime(threshold, zabbixResponseDtoList, zabbixResponseDto, "37412");
                if ((Boolean) checkTime.get(0)) {
                    Alarm alarm = new Alarm();
                    alarm.setCategory(threshold.getCategory());
                    alarm.setTimePeriodInSec(checkTime.get(1).toString());
                    alarm.setDate(new Date());
                    alarm.setIp(threshold.getIp());
                    alarm.setThresholdUUID(threshold.getThresholdUUID());
                    //this is available memory
                    alarm.setMemoryUsage(Double.parseDouble(zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37411")).toList().get(0).getLastvalue()));
                    return alarm;
                }
            }
        }
        return null;
    }

    private Alarm checkHard(ZabbixResponseDto zabbixResponseDto, List<ZabbixResponseDto> zabbixResponseDtoList, Threshold threshold) {
        List<ZabbixResultItemDto> spaceUtilization = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37608")).toList();
        if (!spaceUtilization.isEmpty()) {
            if (threshold.getPercentage() <= Double.parseDouble(spaceUtilization.get(0).getLastvalue())) {
                List checkTime = checkTime(threshold, zabbixResponseDtoList, zabbixResponseDto, "37608");
                if ((Boolean) checkTime.get(0)) {
                    Alarm alarm = new Alarm();
                    alarm.setCategory(threshold.getCategory());
                    alarm.setTimePeriodInSec(checkTime.get(1).toString());
                    alarm.setIp(threshold.getIp());
                    alarm.setDate(new Date());
                    alarm.setThresholdUUID(threshold.getThresholdUUID());
                    alarm.setMemoryUsage(Double.parseDouble(spaceUtilization.get(0).getLastvalue()));
                    return alarm;
                }
            }
        }
        return null;
    }

    private Alarm checkCpu(ZabbixResponseDto zabbixResponseDto, List<ZabbixResponseDto> zabbixResponseDtoList, Threshold threshold) {
        List<ZabbixResultItemDto> cpu = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals("37415")).toList();
        if (!cpu.isEmpty()) {
            if (threshold.getPercentage() <= Double.parseDouble(cpu.get(0).getLastvalue())) {
                List checkTime = checkTime(threshold, zabbixResponseDtoList, zabbixResponseDto, "37415");
                if ((Boolean) checkTime.get(0)) {
                    Alarm alarm = new Alarm();
                    alarm.setCategory(threshold.getCategory());
                    alarm.setTimePeriodInSec(checkTime.get(1).toString());
                    alarm.setIp(threshold.getIp());
                    alarm.setDate(new Date());
                    alarm.setThresholdUUID(threshold.getThresholdUUID());
                    alarm.setCpuLoad(Double.parseDouble(cpu.get(0).getLastvalue()));
                    return alarm;
                }
            }
        }
        return null;
    }

    private List checkTime(Threshold threshold, List<ZabbixResponseDto> zabbixResponseDtoList, ZabbixResponseDto lastOne, String itemId) {
        AtomicBoolean lastResult = new AtomicBoolean(false);
        AtomicBoolean breakFromLoop = new AtomicBoolean(false);
        AtomicLong atomicLong = new AtomicLong(0);
        zabbixResponseDtoList.forEach(zabbixResponseDto -> {
            if (!lastResult.get() && !breakFromLoop.get()) {
                List<ZabbixResultItemDto> result = zabbixResponseDto.getResult().stream().filter(obj -> obj.getItemid().equals(itemId)).toList();
                if (threshold.getPercentage() <= Double.parseDouble(result.get(0).getLastvalue())) {
                    Long seconds = calculateSecondsBetweenDates(zabbixResponseDto.getDate(), lastOne.getDate());
                    if (threshold.getTimePeriod() <= seconds) {
                        lastResult.set(true);
                        atomicLong.set(seconds);
                    }
                } else {
                    breakFromLoop.set(true);
                }
            }
        });
        List list = new ArrayList();
        list.add(lastResult.get());
        list.add(atomicLong.get());

        return list;
    }


    private Long calculateSecondsBetweenDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        // convert the time difference to seconds
        long diffSeconds = diff / 1000;
        return diffSeconds;
    }

}
