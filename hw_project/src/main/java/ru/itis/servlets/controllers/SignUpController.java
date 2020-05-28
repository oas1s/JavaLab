package ru.itis.servlets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.servlets.dto.SignUpDto;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.UserRepositoryImpl;

import javax.validation.Valid;


@Controller
public class SignUpController {

    @Autowired
    UserRepositoryImpl userRepository;

    @GetMapping("/signUp")
    public String getSignUpPage(Model model){
        model.addAttribute("signUpDto",new SignUpDto());
        return "reg";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid SignUpDto signUpDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("signUpDto", signUpDto);
            return "reg";
        } else {

            User user = User.builder()
                    .email(signUpDto.getEmail())
                    .password(signUpDto.getPassword())
                    .build();

            userRepository.save(user);

            return "redirect:/signIn";
        }
    }
}
