package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank(message = "Имя не должно быть пустым.")
    private String name;
    @Email(message = "Некорректный email")
    @NotBlank(message = "Адрес электронной почты не должен быть пустым.")
    private String email;
    @PastOrPresent(message = "День рождения не может быть в будущем.")
    private LocalDate birthday;
    @NotBlank(message = "Логин не должен быть пустым.")
    @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелы")
    private String login;
}
