package ru.itis.servlets.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.servlets.dto.MessageDto;
import ru.itis.servlets.dto.UserDto;
import ru.itis.servlets.models.User;
import ru.itis.servlets.security.details.UserDetailsImpl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    private static final Set<WebSocketSession>  sessions = new HashSet<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageText = (String) message.getPayload();
        MessageDto messageDtoFromJson = objectMapper.readValue(messageText, MessageDto.class);
        UserDetailsImpl userDetails = (UserDetailsImpl) session.getPrincipal();
        User u = userDetails.getUser();
        messageDtoFromJson.setFrom(u);


        sessions.add(session);

        for (WebSocketSession currentSession : sessions) {
            currentSession.sendMessage(message);
        }
    }
}