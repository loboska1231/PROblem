package org.copyria2.order_service.services;

import lombok.RequiredArgsConstructor;
import org.copyria2.order_service.dto.MailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    public void sendEmail(MailDto mailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(mailDto.to());
        mailMessage.setSubject(mailDto.title());
        mailMessage.setText(mailDto.message());

        mailSender.send(mailMessage);
    }

}
