package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Film {
    private Long id;
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;
    @Size(max = 200, message = "Описание фильма не может превышать 200 символов.")
    private String description;
    @NotNull(message = "Не указана дата выхода фильма.")
    @ReleaseDateNotBefore(value = "1895-12-28")
    private LocalDate releaseDate;
    @PositiveOrZero(message = "Фильм не может длиться отрицательное количество времени.")
    private int duration;
    private Long rating;
    private int likes;
}