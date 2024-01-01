package com.submodule.rabbit;

import com.google.gson.Gson;
import com.submodule.dto.ConsumerDto;
import com.submodule.entity.Threshold;
import com.submodule.enums.IpRanges;
import com.submodule.kafka.Producer;
import com.submodule.service.ThresholdService;
import com.submodule.utils.IpUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        Threshold obj = gson.fromJson(threshold, Threshold.class);
        try {

                thresholdService.save(obj);
                latch.countDown();
//                ConsumerDto consumerDto = new ConsumerDto(ipUtil.chooseKafkaTopicForSendToWorker(obj).name(), obj);
//                producer.sendMessage(consumerDto, consumerDto.getTopicName());
        } catch (Exception exception) {
            System.out.println("something bad occurred");
        }

    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
