package ru.itis.sem.services;

import org.springframework.stereotype.Service;
import ru.itis.sem.models.User;

@Service
public interface MailSenderService {
    public void sendMail(User user);
}
