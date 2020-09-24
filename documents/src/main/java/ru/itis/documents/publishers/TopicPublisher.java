package ru.itis.documents.publishers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicPublisher {

    private final static String PNG_ROUTING_KEY = "files.images.png";

    private final static String FILES_EXCHANGE = "files_topic_exchange";
    private final static String EXCHANGE_TYPE = "topic";

    public static void publish(String message) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(FILES_EXCHANGE, EXCHANGE_TYPE);

            String  currentRouting = PNG_ROUTING_KEY;
            channel.basicPublish(FILES_EXCHANGE, currentRouting, null, message.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}