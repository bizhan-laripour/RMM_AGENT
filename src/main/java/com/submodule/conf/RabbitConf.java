package com.submodule.conf;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitConf {

    public final static String alarmQueue = "ALARM";
    public final static String rmmQueue = "RMM" ;
    public final static String exchange = "RMM-exchange";
    public final static String routingAlarmKey = "alarm_routing_key";

    public final static String routingRmmKey = "rmm_routing_key";

    // spring bean for rabbitmq queue
    @Bean
    public Queue AlarmQueue(){
        return new Queue(alarmQueue);
    }

    // spring bean for queue (store json messages)
    @Bean
    public Queue rmmQueue(){
        return new Queue(rmmQueue);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(AlarmQueue())
                .to(exchange())
                .with(routingAlarmKey);
    }

    // binding between json queue and exchange using routing key
    @Bean
    public Binding jsonBinding(){
        return BindingBuilder
                .bind(rmmQueue())
                .to(exchange())
                .with(routingRmmKey);
    }



    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}
