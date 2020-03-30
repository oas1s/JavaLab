package ru.itis.servlets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.servlets.dto.SignUpDto;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.UserRepositoryImpl;

@Controller
public class SignUpController {

    @Autowired
    UserRepositoryImpl userRepository;

    @GetMapping("/signUp")
    public String getSignUpPage(){
        return "reg";
    }

    @PostMapping("/signUp")
    public String signUp(SignUpDto signUpDto) {

        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .build();

        userRepository.save(user);

        return "redirect:/signIn";
    }
}
