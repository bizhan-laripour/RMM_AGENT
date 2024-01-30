package com.submodule.rabbit;

import com.google.gson.Gson;
import com.submodule.entity.Threshold;
import com.submodule.service.ThresholdService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private final ThresholdService thresholdService;
    private final Gson gson;


    private CountDownLatch latch = new CountDownLatch(1);

    public Receiver(ThresholdService thresholdService, Gson gson) {
        this.thresholdService = thresholdService;
        this.gson = gson;

    }


    @Transactional
    @RabbitListener(queues = {"RMM"})
    public void receiveMessage(String threshold) {
        Threshold obj = gson.fromJson(threshold, Threshold.class);
        System.out.println("recieved object is " + obj);
        try {
            thresholdService.save(obj);
            latch.countDown();
        } catch (Exception exception) {
            System.out.println("something bad occurred");
        }

    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
