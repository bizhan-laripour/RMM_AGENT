package com.submodule.rabbit;

import com.google.gson.Gson;
import com.submodule.entity.Alarm;
import com.submodule.entity.Threshold;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class AlarmMockListener {


    private final Gson gson;

    public AlarmMockListener(Gson gson) {
        this.gson = gson;
    }

    @RabbitListener(queues = {"ALARM"})
    public void consume(String message){
        List<Alarm> obj = gson.fromJson(message, List.class);
        obj.forEach(System.out::println);
    }
}
