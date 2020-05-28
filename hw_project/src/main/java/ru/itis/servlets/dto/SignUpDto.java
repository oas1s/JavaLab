package ru.itis.servlets.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SignUpDto {
    @Email(message = "Email введен некорректно")
    private String email;

    @NotNull(message = "не может быть пустым")
    @Min(value = 3, message = "Пароль слишком короткий")
    private String password;
}

