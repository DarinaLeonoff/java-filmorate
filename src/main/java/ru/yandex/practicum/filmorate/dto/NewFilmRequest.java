package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class NewFilmRequest {
    private String name;
    private String description;
    private int duration;
    private LocalDate releaseDate;

    private Long ratingId;
    private List<Long> genres;
}
