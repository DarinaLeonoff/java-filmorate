package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
public class UpdateFilmRequest {
    private Long id;
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;
    @Size(max = 200, message = "Описание не может превышать 200 символов.")
    private String description;
    @ReleaseDateNotBefore(value = "1895-12-28")
    private LocalDate releaseDate;
    @PositiveOrZero(message = "Длительность не может быть отрицательной.")
    private Integer duration;
    private Mpa mpa;
    private List<Genre> genres;

    public boolean hasName() {
        return !(name == null || name.isBlank());
    }

    public boolean hasDescription() {
        return !(description == null || description.isBlank());
    }

    public boolean hasDuration() {
        return !(duration == 0);
    }

    public boolean hasReleaseDate() {
        return !(releaseDate == null);
    }

    public boolean hasMpa() {
        return mpa != null;
    }

    public boolean hasGenres() {
        return genres != null && !genres.isEmpty();
    }
}
