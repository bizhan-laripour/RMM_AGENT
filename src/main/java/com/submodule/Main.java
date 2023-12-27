package com.submodule;

import com.google.gson.Gson;
import com.submodule.conf.RabbitConf;
import com.submodule.entity.Threshold;
import com.submodule.receiver.Receiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Main implements CommandLineRunner {

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

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        Threshold threshold = new Threshold();
        threshold.setIp("127.0.0.1");
        Gson gson = new Gson();
        String obj = gson.toJson(threshold);
        rabbitTemplate.convertAndSend(RabbitConf.topicExchangeName, "foo.bar.baz", obj);
        Thread.sleep(3000);
    }
}