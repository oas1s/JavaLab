package ru.itis.servlets.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.servlets.models.User;

@Data
@Builder
public class MessageDto {
    public MessageDto(String text, User from) {
        this.text = text;
        this.from = from;
    }

    public MessageDto() {
    }

    public MessageDto(String text) {
        this.text = text;
    }

    public MessageDto(User from) {
        this.from = from;
    }

    private String text;
    private User from;
}