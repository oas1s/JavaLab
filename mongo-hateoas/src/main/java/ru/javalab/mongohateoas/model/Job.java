package ru.javalab.mongohateoas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "job")
public class Job {
    @Id
    private String _id;
    private String name;
    private String slogan;
}
