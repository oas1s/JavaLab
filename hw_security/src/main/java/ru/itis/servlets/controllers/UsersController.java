package ru.itis.servlets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.UserRepositoryImpl;

import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UserRepositoryImpl userRepository;


    @GetMapping("/users")
    public String getUsersPage(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

}
