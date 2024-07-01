package com.envio.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeMessage(String to, String name) {
        String subject = "Bienvenido a nuestra tienda";
        String htmlContent = String.format(
                "<div style='text-align: center;'>"
                        + "<h1>Bienvenido %s a nuestra tienda</h1>"
                        + "<p>¡Estamos encantados de tenerte con nosotros! Disfruta de un <strong>30%% de descuento</strong> en todas nuestras prendas de hombre y de mujer.</p>"
                        + "<p>Visítanos pronto y aprovecha esta oferta exclusiva.</p>"
                        + "<p>Saludos,<br>Tu tienda favorita</p>"
                        + "<a href='http://localhost:4200/' style='display: inline-block; padding: 10px 20px; font-size: 16px; color: white; background-color: #007BFF; text-decoration: none; border-radius: 5px;'>Visítanos</a>"
                        + "</div>",
                name);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
}
