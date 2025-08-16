package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final Logger log = LoggerFactory.getLogger(FilmController.class);

    @PostMapping
    public Film add(@RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmService.getAll();
    }

    @DeleteMapping
    public void delete(@RequestBody Film film) {
        filmService.deleteFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(Long id) {
        return filmService.getFilm(id);
    }

    @PutMapping("/{id}/likes/{userId}")
    public Film setLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.setLike(id, userId);
    }

    @DeleteMapping("/{id}/likes/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/top10")
    public Collection<Film> getTopTen() {
        return filmService.getTopTen();
    }
}
