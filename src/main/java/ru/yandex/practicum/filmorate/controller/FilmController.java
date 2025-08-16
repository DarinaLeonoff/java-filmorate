package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.sevice.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
    private final FilmService filmService = new FilmService(filmStorage);
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

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        return filmStorage.getFilm(id);
    }

    @PutMapping("/{id}/likes/{userId}")
    public Film setLike(@PathVariable Long id, @PathVariable Long userId) {
        Film film = filmStorage.getFilm(id);
        return filmService.addLike(film, userId);
    }

    @DeleteMapping("/{id}/likes/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        Film film = filmStorage.getFilm(id);
        return filmService.deleteLike(film, userId);
    }

    @GetMapping("/top10")
    public Collection<Film> getTopTen(){
        return filmService.getTopTen();
    }
}
