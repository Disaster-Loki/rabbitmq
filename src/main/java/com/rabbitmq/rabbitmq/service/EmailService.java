package com.rabbitmq.rabbitmq.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.rabbitmq.rabbitmq.dto.MailBody;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.enabled:true}")
    private boolean emailEnabled;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendHtmlMessage(MailBody mailBody) {
        if (!emailEnabled) return;

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(mailBody.to());
            helper.setFrom(from);
            helper.setSubject(mailBody.subject());
            helper.setText(mailBody.htmlText(), true);

            javaMailSender.send(message);
            System.out.printf("✅ Email enviado com sucesso para: %s%n", mailBody.to());
        } catch (Exception e) {
            System.out.printf("❌ Erro inesperado ao enviar email: %s%n", e.getMessage());
        }
    }
}
