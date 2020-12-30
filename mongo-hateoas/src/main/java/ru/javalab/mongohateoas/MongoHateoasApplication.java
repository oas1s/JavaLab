package ru.javalab.mongohateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "ru.javalab.mongohateoas")
public class MongoHateoasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoHateoasApplication.class, args);
    }

}
