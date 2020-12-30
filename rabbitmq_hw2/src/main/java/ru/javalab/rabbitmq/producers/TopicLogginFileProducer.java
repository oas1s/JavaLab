package ru.javalab.rabbitmq.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;
import ru.javalab.rabbitmq.models.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@Component
public class TopicLogginFileProducer {

    private final static String LOG_ROUTING_KEY = "files.log.reg";

    private final static String FILES_EXCHANGE = "log_topic_exchange";
    private final static String EXCHANGE_TYPE = "topic";

    public void addLog(User user, String routing) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(FILES_EXCHANGE, EXCHANGE_TYPE);

            String currentRouting = routing.equals(LOG_ROUTING_KEY) ? LOG_ROUTING_KEY : null;

            if (currentRouting == null) {
                throw new IllegalArgumentException();
            }

            String jsonUser = new ObjectMapper().writeValueAsString(user);

            channel.basicPublish(FILES_EXCHANGE, currentRouting, null, jsonUser.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}