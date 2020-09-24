package ru.itis.documents.publishers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutPublisher {
    // есть EXCHANGE - images НЕ ОЧЕРЕДЬ
    private final static String EXCHANGE_NAME = "images";

    private final static String EXCHANGE_TYPE = "fanout";

    public void publish(String message) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // создаем exchange
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            // открываем файл с картинками
            File file = new File("images.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            // считываем файл URL
                // публикую в exchange
                channel.basicPublish(EXCHANGE_NAME, "",null, message.getBytes());

        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

}