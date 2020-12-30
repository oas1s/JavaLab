package ru.javalab.rabbitmq.consumers.direct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.javalab.rabbitmq.models.User;
import ru.javalab.rabbitmq.repositories.UserRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class DBSaveConsumer {

    private final UserRepository userRepository;

    private final static String DB_QUEUE = "database_add_queue";

    public  void saveUser() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);
            channel.basicConsume(DB_QUEUE, false, (consumerTag, message) -> {
                User user = new ObjectMapper().readValue(new String(message.getBody()), User.class);
                userRepository.save(user);
            }, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}