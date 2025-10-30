package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
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
    public Film add(@Valid @RequestBody Film film) throws InternalServerException {
        return filmService.addFilm(film);
    } //возвращает ошибку 500, но фильм добавляет

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws InternalServerException {
        return filmService.update(film);
    } //415 фильм не гобновляется

    @GetMapping
    public Collection<Film> getAll() {
        return filmService.getAll();
    } //correct

    @DeleteMapping
    public void delete(@Valid @RequestBody Film film) {
        filmService.deleteFilm(film);
    } //400

    @GetMapping("/{id}")
    public Film getFilm(Long id) {
        return filmService.getFilm(id);
    }//404
//
//    @PutMapping("/{id}/like/{userId}")
//    public Film setLike(@PathVariable Long id, @PathVariable Long userId) {
//        return filmService.setLike(id, userId);
//    }
//
//    @DeleteMapping("/{id}/like/{userId}")
//    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
//        return filmService.deleteLike(id, userId);
//    }
//
//    @GetMapping("/popular")
//    public Collection<Film> getTop(@RequestParam(defaultValue = "10") int count) {
//        return filmService.getTop(count);
//    }
}
