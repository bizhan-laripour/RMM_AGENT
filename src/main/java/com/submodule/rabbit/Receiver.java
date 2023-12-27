package com.submodule.rabbit;

import com.google.gson.Gson;
import com.submodule.dto.ConsumerDto;
import com.submodule.entity.Threshold;
import com.submodule.kafka.Producer;
import com.submodule.service.ThresholdService;
import com.submodule.utils.IpUtil;
import org.springframework.stereotype.Component;

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

    public void receiveMessage(String threshold) throws Exception {
        Threshold obj = gson.fromJson(threshold, Threshold.class);
        try {
            thresholdService.save(obj);
            latch.countDown();
            ConsumerDto consumerDto;
            try {
               consumerDto = new ConsumerDto(ipUtil.chooseKafkaTopicForSendToWorker(obj).name(), obj);
            }catch (Exception ex){
                throw ex;
            }
            producer.sendMessage(consumerDto, consumerDto.getTopicName());
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }

    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
