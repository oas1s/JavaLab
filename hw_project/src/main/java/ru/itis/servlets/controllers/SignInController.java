package ru.itis.servlets.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.servlets.security.details.UserDetailsImpl;

import java.security.Principal;

@Controller
public class SignInController {

    @GetMapping("/signIn")
    public String getSignInPage() {
        return "sign_in";
    }
}
