package com.rabbitmq.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OtpProducer {

    private final RabbitTemplate rabbitTemplate;

    public OtpProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOtp(String email, String otp) {
        String message = email + ":" + otp;
        rabbitTemplate.convertAndSend("otp-exchange", "otp.routingkey", message);
        System.out.println("Mensagem enviada: " + message);
    }
}
