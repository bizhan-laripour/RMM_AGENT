package com.submodule;

import com.google.gson.Gson;
import com.submodule.conf.RabbitConf;
import com.submodule.entity.Threshold;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class Main  {

    private final RabbitTemplate rabbitTemplate;

    public Main(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class , args);
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }

//    @Bean
//    public NewTopic workerOneTopic() {
//        return TopicBuilder.name("WORKER_ONE")
//                .build();
//    }
//
//    @Bean
//    public NewTopic workerTwoTopic() {
//        return TopicBuilder.name("WORKER_TWO")
//                .build();
//    }
//
//    @Bean
//    public NewTopic workerThreeTopic() {
//        return TopicBuilder.name("WORKER_THREE")
//                .build();
//    }

//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Sending message...");
//        Threshold threshold = new Threshold();
//        threshold.setIp("192.168.25.1");
//        Gson gson = new Gson();
//        String obj = gson.toJson(threshold);
//        rabbitTemplate.convertAndSend(RabbitConf.topicExchangeName, "foo.bar.baz", obj);
//        Thread.sleep(3000);
//    }
}