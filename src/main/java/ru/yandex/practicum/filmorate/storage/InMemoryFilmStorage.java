package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Long, Film> films = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);

    @Override
    public Film add(@Valid Film film) {
        log.info("Start to create film {}", film.getName());
        film.setId(newId());
        films.put(film.getId(), film);
        log.debug("Film was added with id = {}", film.getId());
        return film;
    }

    @Override
    public Film update(@Valid Film film) {
        containFilm(film.getId());
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

    private boolean containFilm(Long id) {
        if (!films.containsKey(id)) {
            log.warn("No film with id = {}", id);
            throw new NoCandidatesFoundException("Фильм не найден");
        }
        return true;
    }

}
