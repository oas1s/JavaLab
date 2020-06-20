package ru.itis.sem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import ru.itis.sem.dto.SignUpDto;
import ru.itis.sem.models.User;

@Service
@EnableAsync
public class MailSenderServiceImpl implements MailSenderService{

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendMail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject("Auth mail");
        message.setText("http:localhost:8080/auth?uuid="+ user.getUuid());

        javaMailSender.send(message);
    }
}
