package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;
    @Email(message = "Некорректный email")
    @NotBlank(message = "Адрес электронной почти не должен быть пустым.")
    private String email;
    @NotBlank(message = "Логин не должен быть пустым.")
    @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелы")
    private String login;
    @NotBlank(message = "Имя не должно быть пустым.")
    private String name;
    @PastOrPresent(message = "День рождения не может быть в будущем.")
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public boolean setFriend(Long userId) {
        return friends.add(userId);
    }

    public boolean deleteFriend(Long userId) {
        if (!friends.contains(userId)) {
            return false;
        }
        friends.remove(userId);
        return true;
    }
}
