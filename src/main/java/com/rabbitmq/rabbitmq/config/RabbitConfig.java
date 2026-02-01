package com.rabbitmq.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_OTP = "otp-queue";
    public static final String EXCHANGE_OTP = "otp-exchange";
    public static final String ROUTING_KEY_OTP = "otp.routingkey";

    @Bean
    public Queue otpQueue() {
        return new Queue(QUEUE_OTP, true);
    }

    @Bean
    public DirectExchange otpExchange() {
        return new DirectExchange(EXCHANGE_OTP);
    }

    @Bean
    public Binding otpBinding(Queue otpQueue, DirectExchange otpExchange) {
        return BindingBuilder.bind(otpQueue).to(otpExchange).with(ROUTING_KEY_OTP);
    }
}
