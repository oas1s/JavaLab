package ru.itis.servlets.models;

import lombok.Data;

@Data
public class Message {
    private String text;
    private String from;
    private Integer to;
}
