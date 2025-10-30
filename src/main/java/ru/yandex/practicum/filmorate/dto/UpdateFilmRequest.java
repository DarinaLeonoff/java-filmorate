package ru.yandex.practicum.filmorate.dto;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.MPA;

import java.time.LocalDate;
@Data
public class UpdateFilmRequest {
    private String name;
    private String description;
    private int duration;
    private LocalDate releaseDate;
    private int ratingId;

    public boolean hasName(){
        return !(name == null || name.isBlank());
    }
    public boolean hasDescription(){
        return !(description == null || description.isBlank());
    }
    public boolean hasDuration(){
        return !(duration == 0);
    }
    public boolean hasReleaseDate(){
        return !(releaseDate == null);
    }
    public boolean hasRating(){
        return !(ratingId == 0);
    }
}
