package ru.itis.servlets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.PostRepositoryImpl;
import ru.itis.servlets.repositories.UserRepositoryImpl;
import ru.itis.servlets.security.details.UserDetailsImpl;

@Controller
public class TestController {

    @Autowired
    private PostRepositoryImpl postRepository;

    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("post",postRepository.find(1));
        return "test";
    }

    @GetMapping("/test2")
    public String test2(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails.getUser().getId());
        return "test";
    }

    @GetMapping("/test3")
    public String test3(Model model){
        User user = new User();
        user.setId(1);
        user.setEmail("azat@mail.ru");
        user.setPassword("azat");
        model.addAttribute("posts",postRepository.findByUser(user));
        return "test_list";
    }
}
