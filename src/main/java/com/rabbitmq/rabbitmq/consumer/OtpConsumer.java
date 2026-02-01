package com.rabbitmq.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.rabbitmq.rabbitmq.service.EmailService;
import com.rabbitmq.rabbitmq.dto.MailBody;

@Service
public class OtpConsumer {

    private final EmailService emailService;

    public OtpConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "otp-queue")
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida: " + message);

        String[] parts = message.split(":");
        if (parts.length != 2) {
            System.out.println("Mensagem mal formada: " + message);
            return;
        }

        String email = parts[0];
        String otp = parts[1];

        // HTML estilizado formal
        String html = """
        <html>
        <body style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color:#f4f4f7; margin:0; padding:0;">
            <table width="100%%" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center">
                        <table width="600" cellpadding="20" cellspacing="0" style="background-color:#ffffff; border-radius:10px; margin-top:30px; box-shadow:0 2px 8px rgba(0,0,0,0.1);">
                            <tr>
                                <td align="center">
                                    <img src="https://via.placeholder.com/150x50?text=Logo" alt="Logo" style="margin-bottom:20px;">
                                    <h2 style="color:#2E86C1; margin-bottom:10px;">Código OTP de Verificação</h2>
                                    <p style="font-size:16px; color:#555;">Olá,</p>
                                    <p style="font-size:16px; color:#555;">Você solicitou um código de verificação. Use o código abaixo para completar sua autenticação:</p>
                                    <div style="background-color:#f0f0f0; text-align:center; padding:15px; border-radius:5px; margin:20px 0;">
                                        <span style="font-size:24px; font-weight:bold; color:#2E86C1;">%s</span>
                                    </div>
                                    <p style="font-size:14px; color:#777;">Este código é válido por 10 minutos. Não compartilhe com ninguém.</p>
                                    <p style="font-size:14px; color:#777;">Atenciosamente,<br>Equipe RabbitMQ Test</p>
                                    <hr style="border:none; border-top:1px solid #eee; margin:20px 0;">
                                    <p style="font-size:12px; color:#aaa;">Este é um email automático, por favor não responda.</p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(otp);

        // Envia o OTP via email HTML
        emailService.sendHtmlMessage(
            MailBody.builder()
                .to(email)
                .subject("Código OTP para autenticação - RabbitMQ Test")
                .text("Seu código OTP é: " + otp) // fallback plain text
                .htmlText(html)
                .build()
        );
    }
}
