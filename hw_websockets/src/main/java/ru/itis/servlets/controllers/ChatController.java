package ru.itis.servlets.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.servlets.dto.UserDto;
import ru.itis.servlets.models.User;
import ru.itis.servlets.security.details.UserDetailsImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class ChatController {
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/chatPage")
    public String getIndexPage(@RequestParam(required = false) Integer id, HttpServletRequest request, Model model) throws JsonProcessingException {
        Cookie[] cookies = request.getCookies();
        Map<String, String> map = new HashMap<>();
        if (cookies.length >0) {
            Arrays.stream(cookies).forEach(c -> map.put(c.getName(),c.getValue()));
        }
        if (map.size() == 0) {
            return "redirect:/signUpChat";
        } else {
            model.addAttribute("pageId", map.get("email"));
            if (id == null) {
                model.addAttribute("chatId",0);
            } else {
                model.addAttribute("chatId",id);
            }
            return "chat";
        }
    }
}
