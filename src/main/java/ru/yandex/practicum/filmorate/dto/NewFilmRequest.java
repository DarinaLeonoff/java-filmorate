package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.ReleaseDateNotBefore;

import java.time.LocalDate;
import java.util.List;

@Data
@Validated
public class NewFilmRequest {
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;
    @Size(max = 200, message = "Описание не может превышать 200 символов.")
    private String description;
    @NotNull(message = "Не указана дата выхода фильма.")
    @ReleaseDateNotBefore(value = "1895-12-28")
    private LocalDate releaseDate;
    @PositiveOrZero(message = "Длительность не может быть отрицательной.")
    private int duration;

    private Mpa mpa;
    private List<Genre> genres;
}
