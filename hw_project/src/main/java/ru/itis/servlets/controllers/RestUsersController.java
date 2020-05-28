package ru.itis.servlets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.UserRepositoryImpl;

import java.util.List;

@RestController
public class RestUsersController {

    @Autowired
    private UserRepositoryImpl userRepository;

    @GetMapping("/usersAdm")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
