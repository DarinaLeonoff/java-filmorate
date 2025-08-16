package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Long> likes = new HashSet<>();

    public void setLike(Long userId) {
        if (likes.contains(userId)) {
            throw new AlreadyExistsException("Данный пользователь уже лайкнул фильм.");
        }
        likes.add(userId);
    }

    public void deletLike(Long userId) {
        if (!likes.contains(userId)) {
            throw new NoCandidatesFoundException("Данный пользователь не ставил лайк фильму");
        }
        likes.remove(userId);
    }
}
