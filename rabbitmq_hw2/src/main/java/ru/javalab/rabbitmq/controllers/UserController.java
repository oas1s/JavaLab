package ru.javalab.rabbitmq.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javalab.rabbitmq.consumers.direct.DBSaveConsumer;
import ru.javalab.rabbitmq.consumers.fanout.EmailSenderConsumer;
import ru.javalab.rabbitmq.consumers.fanout.VacationDocCreateConsumer;
import ru.javalab.rabbitmq.consumers.topic.UserLogSaveConsumer;
import ru.javalab.rabbitmq.models.User;
import ru.javalab.rabbitmq.producers.DirectDBUserAddProducer;
import ru.javalab.rabbitmq.producers.FanoutExchangeProducer;
import ru.javalab.rabbitmq.producers.TopicLogginFileProducer;

@Controller
@RequiredArgsConstructor
public class UserController {
    // FANOUT INJECTION
    private final EmailSenderConsumer emailSenderConsumer;
    private final VacationDocCreateConsumer vacationDocCreateConsumer;
    private final FanoutExchangeProducer fanoutExchangeProducer;

    // DIRECT INJECTION
    private final DBSaveConsumer dbSaveConsumer;
    private final DirectDBUserAddProducer directDbUserAddProducer;

    // TOPIC INJECTION
    private final UserLogSaveConsumer userLogSaveConsumer;
    private final TopicLogginFileProducer topicLogginFileProducer;

    @GetMapping("/reguser")
    public String regUser(Model model){
        return "reguser";
    }

    @PostMapping("/reguser")
    public String regUserCreate(User user){
        // FANOUT EXCHANGE
        emailSenderConsumer.sendMail(user.getMail());
        vacationDocCreateConsumer.createDocs();
        fanoutExchangeProducer.registerUser(user.getName() + user.getSurname());

        // DIRECT EXCHANGE
//        dbSaveConsumer.saveUser();
//        directDbUserAddProducer.addUserToDataBase(user,"db");

        // TOPIC EXCHANGE
//        userLogSaveConsumer.logUser();
//        topicLogginFileProducer.addLog(user,"files.log.reg");
        return "redirect:/";
    }
}
