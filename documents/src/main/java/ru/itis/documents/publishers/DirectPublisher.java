package ru.itis.documents.publishers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectPublisher {
    // есть очередь для PNG
    private final static String PNG_QUEUE = "images_png_queue";
    // роутинг по png
    private final static String PNG_ROUTING_KEY = "png";
    private final static String IMAGES_EXCHANGE = "images_direct_exchange";
    // новый
    private final static String EXCHANGE_TYPE = "direct";

    public static void publish(String message) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // создали Exchange
            channel.exchangeDeclare(IMAGES_EXCHANGE, EXCHANGE_TYPE);
            // привязали очереди под определенным routingKey
            channel.queueBind(PNG_QUEUE, IMAGES_EXCHANGE, PNG_ROUTING_KEY);

            String currentRouting = "png";

            channel.basicPublish(IMAGES_EXCHANGE, currentRouting, null, message.getBytes());

        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}