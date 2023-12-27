package com.submodule.kafka;

import com.submodule.dto.ConsumerDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Component
public class Producer {

    private final KafkaTemplate<String, ConsumerDto> kafkaTemplate;

    public Producer(KafkaTemplate<String, ConsumerDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ConsumerDto result , String topicName) {
        kafkaTemplate.send(topicName, result);

    }
}
