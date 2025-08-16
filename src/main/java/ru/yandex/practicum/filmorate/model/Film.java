package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.exception.AlreadyExistsException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Data
@RequiredArgsConstructor
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Collection<Long> likes = new HashSet<>();

    public void setLike(Long userId){
       if(likes.contains(userId)){
           throw new AlreadyExistsException("Данный пользователь уже лайкнул фильм.");
       }
       likes.add(userId);
    }
}
