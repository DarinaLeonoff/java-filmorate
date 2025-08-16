package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Long, Film> films = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);

    @Override
    public Film add(Film film) {
        filmValidation(film);
        log.info("Start to create film {}", film.getName());
        film.setId(newId());
        films.put(film.getId(), film);
        log.debug("Film was added with id = {}", film.getId());
        return film;
    }

    @Override
    public Film update(Film film) {
        containFilm(film.getId());
        filmValidation(film);
        films.put(film.getId(), film);
        log.debug("Film with id = {} was updated", film.getId());
        return film;
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public Film getFilm(Long id) {
        containFilm(id);
        return films.get(id);
    }

    @Override
    public void deleteFilm(Film film) {
        if (containFilm(film.getId())) {
            films.remove(film.getId());
        }
    }

    private Long newId() {
        return films.keySet().stream().mapToLong(id -> id).max().orElse(0) + 1;
    }

    private void filmValidation(Film film) {
        String name = film.getName();
        if (name == null || name.isBlank()) {
            log.warn("Try to add empty film name");
            throw new ValidationException("Название фильма не может быть пустым.");
        }

        int descLength = film.getDescription().length();
        if (descLength > 200) {
            log.warn("Description longer then 200");
            throw new ValidationException("Описание фильма не может превышать 200 символов.");
        }

        LocalDate date = film.getReleaseDate();
        if (date == null) {
            log.warn("Date is null");
            throw new ValidationException("Не указана дата выхода фильма.");
        }
        if (date.isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Release date is before 28/12/1895");
            throw new ValidationException("Фильм не может быть выпущен до 28.12.1895.");
        }

        if (film.getDuration() < 0) {
            log.warn("Duration is under 0");
            throw new ValidationException("Фильм не может длиться отрицательное количество времени.");
        }
    }

    private boolean containFilm(Long id) {
        if (!films.containsKey(id)) {
            log.warn("No film with id = {}", id);
            throw new NoCandidatesFoundException("Фильм не найден");
        }
        return true;
    }

}
