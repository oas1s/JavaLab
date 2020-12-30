package ru.javalab.rabbitmq.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ru.javalab.rabbitmq.models.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class DirectDBUserAddProducer {

    // есть отдельная очередь для JPG
    private final static String DB_QUEUE = "database_add_queue";
    // роутинг по jpg
    private final static String DB_ROUTING_KEY = "db";
    // есть новый экчендж
    private final static String DB_EXCHANGE = "database_add_exchange";
    // новый
    private final static String EXCHANGE_TYPE = "direct";

    public  void addUserToDataBase(User user, String routingKey) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // создали Exchange
            channel.exchangeDeclare(DB_EXCHANGE, EXCHANGE_TYPE);
            // привязали очереди под определенным routingKey
            channel.queueBind(DB_QUEUE, DB_EXCHANGE, DB_ROUTING_KEY);

            String currentRouting = routingKey.equals(DB_ROUTING_KEY) ? DB_ROUTING_KEY : null;

            if (currentRouting == null) {
                throw new IllegalArgumentException();
            }

            String jsonUser = new ObjectMapper().writeValueAsString(user);

            channel.basicPublish(DB_EXCHANGE, currentRouting, null, jsonUser.getBytes());

        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}