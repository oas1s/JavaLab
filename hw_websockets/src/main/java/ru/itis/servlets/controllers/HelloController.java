package ru.itis.servlets.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.servlets.models.User;
import ru.itis.servlets.security.details.UserDetailsImpl;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String helloPage(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "welcome";
    }
}
