package com.submodule.controller;

import com.submodule.service.AlarmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmController {


    private final AlarmService alarmService;

    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }


    @RequestMapping(value = "get-active-alarms" , method = RequestMethod.GET)
    public ResponseEntity<?> findByActive(){
        return new ResponseEntity<>(alarmService.findByActive(true) , HttpStatus.OK);
    }

    @RequestMapping(value = "get-deactivate-alarms" , method = RequestMethod.GET)
    public ResponseEntity<?> findByDeactivate(){
        return new ResponseEntity<>(alarmService.findByActive(false) , HttpStatus.OK);
    }
}
