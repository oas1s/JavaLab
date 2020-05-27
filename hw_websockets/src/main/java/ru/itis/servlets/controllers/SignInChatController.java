package ru.itis.servlets.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.servlets.dto.SignUpDto;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.UserRepositoryImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInChatController {

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signInChat")
    public String getSignInPage(HttpServletResponse response, SignUpDto signUpDto) {
        if (userRepository.findUserByEmail(signUpDto.getEmail()).isPresent()) {
            System.out.println("user found");
            User user = userRepository.findUserByEmail(signUpDto.getEmail()).get();
            Cookie cookie = new Cookie("email", signUpDto.getEmail());
            response.addCookie(cookie);
            return "redirect:/chatPage";

        } else {
            System.out.println("user not found");
            return "redirect:/signInChat";
        }
    }

    @GetMapping("/signInChat")
    public String SignIn() {
        return "sign_in_chat";
    }
}
