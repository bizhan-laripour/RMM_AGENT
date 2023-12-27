package com.submodule.receiver;

import com.google.gson.Gson;
import com.submodule.entity.Threshold;
import com.submodule.service.ThresholdService;
import org.springframework.stereotype.Component;

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

    public void receiveMessage(String threshold) {
        Threshold  obj = gson.fromJson(threshold , Threshold.class);
        thresholdService.save(obj);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
