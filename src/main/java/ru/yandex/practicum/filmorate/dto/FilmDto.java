package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.ReleaseDateNotBefore;

import java.time.LocalDate;
import java.util.List;

@Data
public class FilmDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;
    @Size(max = 200, message = "Описание фильма не может превышать 200 символов.")
    private String description;
    @PositiveOrZero(message = "Фильм не может длиться отрицательное количество времени.")
    private int duration;
    @NotNull(message = "Не указана дата выхода фильма.")
    @ReleaseDateNotBefore(value = "1895-12-28")
    private LocalDate releaseDate;
    private Mpa mpa;
    private List<GenreDto> genres;
    private int likes;
}
