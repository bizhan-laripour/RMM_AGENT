package com.submodule.controller;

import com.google.gson.Gson;
import com.submodule.conf.RabbitConf;
import com.submodule.entity.Threshold;
import com.submodule.service.ThresholdService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ThresholdController {

    private final ThresholdService thresholdService;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ThresholdController(ThresholdService thresholdService, RabbitTemplate rabbitTemplate) {
        this.thresholdService = thresholdService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Threshold threshold) {
        return new ResponseEntity<>(thresholdService.save(threshold) , HttpStatus.OK);
    }

    @GetMapping("find-all")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(thresholdService.findAll() , HttpStatus.OK);
    }

    @GetMapping("send")
    public void send(@RequestParam String ip){
        System.out.println("Sending message...");
        Threshold threshold = new Threshold();
        threshold.setIp(ip);
        Gson gson = new Gson();
        String obj = gson.toJson(threshold);
        rabbitTemplate.convertAndSend(RabbitConf.topicExchangeName, "foo.bar.baz", obj);
    }
}
