package ru.javalab.rabbitmq.consumers.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javalab.rabbitmq.models.User;
import ru.javalab.rabbitmq.utils.EmailServiceImpl;
import ru.javalab.rabbitmq.utils.ReplaceStream2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Component
public class EmailSenderConsumer {

    private  final  EmailServiceImpl emailService;

    private final static String EXCHANGE_NAME = "documents_save";
    private final static String EXCHANGE_TYPE = "fanout";

    public void sendMail(String recipient) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);

            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            // создаем временную очередь со случайным названием
            String queue = channel.queueDeclare().getQueue();

            // привязали очередь к EXCHANGE_NAME
            channel.queueBind(queue, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                try {
                    String nameSurname = new String(message.getBody());
                    emailService.prepareAndSend(recipient, "Hello, " + nameSurname);
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (Exception e) {
                    System.err.println("FAILED");
                    channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                }
            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}