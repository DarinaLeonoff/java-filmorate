package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
    private final Logger log = LoggerFactory.getLogger(FilmController.class);

    @PostMapping
    public Film add(@RequestBody Film film) {
        return filmStorage.add(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        return filmStorage.update(film);
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    @DeleteMapping
    public void delete(@RequestBody Film film) {
        filmStorage.deleteFilm(film);
    }

}
