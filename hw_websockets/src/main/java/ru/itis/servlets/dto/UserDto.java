package ru.itis.servlets.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserDto {
    public UserDto(Integer id) {
        this.id = id;
    }

    public UserDto(String name) {
        this.name = name;
    }

    public UserDto(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public UserDto() {
    }

    private String name;
    private Integer id;
}