package com.submodule.kafka;

import com.google.gson.Gson;
import com.submodule.dto.ConsumerDto;
import com.submodule.entity.Threshold;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * this class is for test consuming the kafka
 */
@Component
public class Consumer {

//    @KafkaListener(topics = "WORKER_ONE", containerFactory = "kafkaListenerContainerFactory")
//    public void consumeWorkerOne(ConsumerRecord<String, ConsumerDto> review) {
//        ConsumerDto consumerDto = review.value();
//        Gson gson = new Gson();
//        String thresholdString = gson.toJson(consumerDto.getObject());
//        Threshold threshold = gson.fromJson(thresholdString, Threshold.class);
//        System.out.println("threshold received from worker 1 that is:");
//        System.out.println(threshold);
//    }
//
//    @KafkaListener(topics = "WORKER_TWO", containerFactory = "kafkaListenerContainerFactory")
//    public void consumeWorkerTwo(ConsumerRecord<String, ConsumerDto> review) {
//        ConsumerDto consumerDto = review.value();
//        Gson gson = new Gson();
//        String thresholdString = gson.toJson(consumerDto.getObject());
//        Threshold threshold = gson.fromJson(thresholdString, Threshold.class);
//        System.out.println("threshold received from worker 2 that is:");
//        System.out.println(threshold);
//    }
//
//    @KafkaListener(topics = "WORKER_THREE", containerFactory = "kafkaListenerContainerFactory")
//    public void consumeWorkerThree(ConsumerRecord<String, ConsumerDto> review) {
//        ConsumerDto consumerDto = review.value();
//        Gson gson = new Gson();
//        String thresholdString = gson.toJson(consumerDto.getObject());
//        Threshold threshold = gson.fromJson(thresholdString, Threshold.class);
//        System.out.println("threshold received from worker 3 that is:");
//        System.out.println(threshold);
//    }
}
