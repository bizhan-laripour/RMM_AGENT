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

    public static void main(String[] args) {
        SpringApplication.run(Main.class , args);
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }

}