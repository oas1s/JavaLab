package ru.itis.servlets.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.servlets.dto.MessageDto;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.MessageRepository;
import ru.itis.servlets.security.details.UserDetailsImpl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




@RestController
public class MessagesController {


    @Autowired
    private MessageRepository messageRepository;

    private static final Map<Integer, List<MessageDto>> messages = new HashMap<>();

    // получили сообщение от какой либо страницы - мы его разошлем во все другие страницы
    @PostMapping("/messages")
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto message, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        message.setUser_id(user.getId());

        boolean isNew = true;
        if (!message.getText().equals("Login")) {
            messageRepository.save(message);
            isNew = false;
        }

        // если сообщений с этой или для этой страницы еще не было
        if (!messages.containsKey(message.getUser_id())) {
            // добавляем эту страницу в Map-у страниц
            messages.put(message.getUser_id(), new ArrayList<>());
        }

        Page<MessageDto> page = messageRepository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC,"id"));
        List<MessageDto> list = messages.get(user.getId());


        // полученное сообщение добавляем для всех открытых страниц нашего приложения
        for (List<MessageDto> pageMessages : messages.values()) {
            // перед тем как положить сообщение для какой-либо страницы
            // мы список сообщений блокируем
            synchronized (pageMessages) {
                // добавляем сообщение
                if (list == pageMessages && isNew) {
                    pageMessages.addAll(page.toList());
                }
                pageMessages.add(message);
                // говорим, что другие потоки могут воспользоваться этим списком
                pageMessages.notifyAll();
            }
        }

        return ResponseEntity.ok().build();
    }

    // получить все сообщения для текущего запроса
    @SneakyThrows
    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getMessagesForPage(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        // получили список сообшений для страницы и заблокировали его
        synchronized (messages.get(user.getId())) {
            // если нет сообщений уходим в ожидание
            if (messages.get(user.getId()).isEmpty()) {
                messages.get(user.getId()).wait();
            }
        }

        // если сообщения есть - то кладем их в новых список
        List<MessageDto> response = new ArrayList<>(messages.get(user.getId()));
        // удаляем сообщения из мапы
        messages.get(user.getId()).clear();
        return ResponseEntity.ok(response);
    }
}
