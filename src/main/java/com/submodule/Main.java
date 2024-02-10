package com.submodule;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
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