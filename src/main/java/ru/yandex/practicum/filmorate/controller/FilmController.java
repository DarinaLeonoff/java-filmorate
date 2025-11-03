package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmLikes;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.LikesService;

import java.util.Collection;
@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final LikesService likesService;

    @PostMapping
    public FilmDto add(@Valid @RequestBody NewFilmRequest request) throws InternalServerException {
        return filmService.addFilm(request);
    }

    @PutMapping
    public FilmDto update(@Valid @RequestBody UpdateFilmRequest request) throws InternalServerException {
        return filmService.update(request);
    }

    @GetMapping
    public Collection<FilmDto> getAll() {
        return filmService.getAll();
    } //correct

    @DeleteMapping
    public void delete(@Valid @RequestBody Film film) {
        filmService.deleteFilm(film);
    } //correct

    @GetMapping("/{id}")
    public FilmDto getFilm(@PathVariable Long id) {
        return filmService.getFilm(id);
    }//correct

    @PutMapping("/{filmId}/like/{userId}")
    public void setLike(@PathVariable Long filmId, @PathVariable Long userId) {
        likesService.setLike(filmId, userId);
    }

    @GetMapping("/{filmId}/likes")
    public FilmLikes setLike(@PathVariable Long filmId) {
        return likesService.getLikes(filmId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmLikes deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        return likesService.deleteLike(id, userId);
    }
//
//    @GetMapping("/popular")
//    public Collection<Film> getTop(@RequestParam(defaultValue = "10") int count) {
//        return filmService.getTop(count);
//    }
}
