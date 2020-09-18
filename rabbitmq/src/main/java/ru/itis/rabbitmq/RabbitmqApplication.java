package ru.itis.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.itis.rabbitmq.utills.ReplaceStream2;

@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RabbitmqApplication.class, args);
    }

}
