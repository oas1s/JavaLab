package ru.javalab.rabbitmq.consumers.topic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;
import ru.javalab.rabbitmq.models.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Component
public class UserLogSaveConsumer {

    private final static String JPG_ROUTING_KEY = "files.log.*";
    private final static String FILES_EXCHANGE = "log_topic_exchange";

    public  void logUser() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, FILES_EXCHANGE, JPG_ROUTING_KEY);
            channel.basicConsume(queueName, false, (consumerTag, message) -> {
                User user = new ObjectMapper().readValue(new String(message.getBody()), User.class);
                String str = "User registered with data " + user;
                BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"));
                writer.write(str);

                writer.close();
            }, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}