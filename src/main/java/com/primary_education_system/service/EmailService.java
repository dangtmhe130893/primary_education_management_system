package com.primary_education_system.service;

import com.primary_education_system.dto.EmailTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);


    public void sendMail(EmailTemplate emailTemplate) {
        new Thread(() -> {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
                mailMsg.setFrom(new InternetAddress("dangtmhe130893@fpt.edu.vn", "Primary Education Management System", "utf-8"));
                mailMsg.setTo(emailTemplate.getReceiver());
                mailMsg.setSubject(emailTemplate.getSubject());
                mailMsg.setText(emailTemplate.getContent(), true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException | UnsupportedEncodingException e) {
                LOGGER.error(e.getMessage());
            }
        }).start();
    }
}
