package ru.javalab.rabbitmq.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
public class EmailServiceImpl  {

    @Autowired
    private JavaMailSender mailSender;

    public void prepareAndSend(String recipient, String message) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("unknownthugger@gmail.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("Sample mail subject");
            messageHelper.setText(message);
        };
        mailSender.send(messagePreparator);
    }
}
