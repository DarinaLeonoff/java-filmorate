package ru.yandex.practicum.filmorate.dto;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateFilmRequest {
    private Long id;
    private String name;
    private String description;
    private int duration;
    private LocalDate releaseDate;
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
