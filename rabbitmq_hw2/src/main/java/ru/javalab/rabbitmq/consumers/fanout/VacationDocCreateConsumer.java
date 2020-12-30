package ru.javalab.rabbitmq.consumers.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.javalab.rabbitmq.utils.ReplaceStream2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class VacationDocCreateConsumer {

    private final static String EXCHANGE_NAME = "documents_save";
    private final static String EXCHANGE_TYPE = "fanout";

    public void createDocs() {
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
                    log.info("STARTED TO CONVERT");
                    String nameSurname = new String(message.getBody());
                    ReplaceStream2.manipulatePdf(nameSurname, "vacation");
                    ReplaceStream2.manipulatePdf(nameSurname, "allocation");
                    ReplaceStream2.manipulatePdf(nameSurname, "dismissal");
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                } catch (Exception e) {
                    log.error("FAILED" + e);
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