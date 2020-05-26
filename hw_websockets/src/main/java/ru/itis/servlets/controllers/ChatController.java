package ru.itis.servlets.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.servlets.dto.UserDto;
import ru.itis.servlets.models.User;
import ru.itis.servlets.security.details.UserDetailsImpl;


@Controller
public class ChatController {
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/chatPage")
    public String getIndexPage(Authentication authentication, Model model) throws JsonProcessingException {
        return "chat_page";
    }
}
