package com.submodule.controller;

import com.submodule.service.AlarmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmController {

    private final AlarmService alarmService;

    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping(value = "find-all")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(alarmService.findAll() , HttpStatus.OK);
    }
}
