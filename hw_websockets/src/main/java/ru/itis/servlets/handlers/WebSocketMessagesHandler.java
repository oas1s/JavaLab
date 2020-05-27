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
import ru.itis.servlets.models.Message;
import ru.itis.servlets.models.User;
import ru.itis.servlets.security.details.UserDetailsImpl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    private static final Map<Integer,Map<String, WebSocketSession>> sessions = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageText = (String) message.getPayload();
        Message messageFromJson = objectMapper.readValue(messageText, Message.class);

        if (!sessions.containsKey(messageFromJson.getTo())) {
            sessions.put(messageFromJson.getTo(),new HashMap<>());
        }
        if (!sessions.get(messageFromJson.getTo()).containsKey(messageFromJson.getFrom())) {
            sessions.get(messageFromJson.getTo()).put(messageFromJson.getFrom(), session);
        }

        for (WebSocketSession currentSession : sessions.get(messageFromJson.getTo()).values()) {
            currentSession.sendMessage(message);
        }
    }
}
