package ru.itis.servlets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.servlets.dto.SignUpDto;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.UserRepositoryImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ChatSignUpController {


    @Autowired
    UserRepositoryImpl userRepository;

    @GetMapping("/signUpChat")
    public String getSignUpChatPage(){
        return "signUpChat";
    }

    @PostMapping("/signUpChat")
    public String signUpByChat(HttpServletResponse response, SignUpDto signUpDto){
        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .build();
        userRepository.save(user);
        return "redirect:/signInChat";
    }
}
