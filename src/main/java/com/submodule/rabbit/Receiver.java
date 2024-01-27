package com.submodule.rabbit;

import com.google.gson.Gson;
import com.submodule.entity.Alarm;
import com.submodule.kafka.Producer;
import com.submodule.service.ThresholdService;
import com.submodule.utils.IpUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private final ThresholdService thresholdService;
    private final Gson gson;

    private final Producer producer;
    private final IpUtil ipUtil;


    private CountDownLatch latch = new CountDownLatch(1);

    public Receiver(ThresholdService thresholdService, Gson gson, Producer producer, IpUtil ipUtil) {
        this.thresholdService = thresholdService;
        this.gson = gson;
        this.producer = producer;
        this.ipUtil = ipUtil;
    }

    @Transactional
    public void receiveMessage(String threshold) throws Exception {
        List<Alarm> obj = gson.fromJson(threshold, List.class);
        System.out.println("recieved object is "+ obj);
//        try {
//                thresholdService.save(obj);
//                latch.countDown();
//        } catch (Exception exception) {
//            System.out.println("something bad occurred");
//        }

    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
