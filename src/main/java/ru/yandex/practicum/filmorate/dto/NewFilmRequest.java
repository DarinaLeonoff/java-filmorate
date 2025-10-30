package ru.yandex.practicum.filmorate.dto;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.MPA;

import java.time.LocalDate;

@Data
public class NewFilmRequest {
    private String name;
    private String description;
    private int duration;
    private LocalDate releaseDate;
    private int ratingId;
}
