package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Название фильма не может быть пустым.")
    private String name;
    @Size(max = 200, message = "Описание фильма не может превышать 200 символов.")
    private String description;
    @NotNull(message = "Не указана дата выхода фильма.")
    @ReleaseDateNotBefore(value = "1895-12-28")
    private LocalDate releaseDate;
    @PositiveOrZero(message = "Фильм не может длиться отрицательное количество времени.")
    private int duration;
    private Set<Long> likes = new HashSet<>();
    private Set<Genre> genres = new HashSet<>();
    private MPA rating;

    public void setLike(Long userId) {
        if (likes.contains(userId)) {
            throw new AlreadyExistsException("Данный пользователь уже лайкнул фильм.");
        }
        likes.add(userId);
    }

    public void deleteLike(Long userId) {
        if (!likes.contains(userId)) {
            throw new NoCandidatesFoundException("Данный пользователь не ставил лайк фильму");
        }
        likes.remove(userId);
    }
}