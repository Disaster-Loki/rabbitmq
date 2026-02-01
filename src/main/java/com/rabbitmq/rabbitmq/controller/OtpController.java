package com.rabbitmq.rabbitmq.controller;

import com.rabbitmq.rabbitmq.producer.OtpProducer;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/otp")
public class OtpController {

    private final OtpProducer otpProducer;
    private final Random random = new Random();

    public OtpController(OtpProducer otpProducer) {
        this.otpProducer = otpProducer;
    }

    @PostMapping("/send")
    public String sendOtp(@RequestParam String email) {
        String otp = String.format("%06d", random.nextInt(999999));
        otpProducer.sendOtp(email, otp);
        return "OTP enviado para fila: " + otp;
    }
}
