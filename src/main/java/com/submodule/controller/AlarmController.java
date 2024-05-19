package com.submodule.controller;

import com.submodule.service.AlarmService;
import com.submodule.specification.AlarmFilterModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmController {

    private final AlarmService alarmService;

    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping(value = "alarm/find-all")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(alarmService.findAll() , HttpStatus.OK);
    }


    @GetMapping(value = "alarm/find-active-alarms")
    public ResponseEntity<?> findActiveAlarms(){
        return new ResponseEntity<>(alarmService.findByActive(true) , HttpStatus.OK);
    }


    @GetMapping(value = "alarm/find-deactive-alarms")
    public ResponseEntity<?> findDeactiveAlarms(){
        return new ResponseEntity<>(alarmService.findByActive(false) , HttpStatus.OK);
    }
    @GetMapping(value = "alarm/find-by-alarm-uuid")
    public ResponseEntity<?> findByUUID(String uuid){
        return new ResponseEntity<>(alarmService.findByUUId(uuid) , HttpStatus.OK);
    }

    @PostMapping(value = "alarm/search")
    public ResponseEntity<?> search(@RequestBody AlarmFilterModel alarmFilterModel) throws IllegalAccessException {
        return ResponseEntity.ok(alarmService.search(alarmFilterModel));
    }


}
