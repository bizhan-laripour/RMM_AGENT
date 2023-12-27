package com.submodule.controller;

import com.submodule.entity.Threshold;
import com.submodule.service.ThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThresholdController {

    private final ThresholdService thresholdService;

    @Autowired
    public ThresholdController(ThresholdService thresholdService) {
        this.thresholdService = thresholdService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Threshold threshold) {
        return new ResponseEntity<>(thresholdService.save(threshold) , HttpStatus.OK);
    }

    @GetMapping("find-all")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(thresholdService.findAll() , HttpStatus.OK);
    }
}
