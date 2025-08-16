package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final InMemoryFilmStorage storage;

    public Film addFilm(Film film) {
        return storage.add(film);
    }

    public Film update(Film film) {
        return storage.update(film);
    }

    public Collection<Film> getAll() {
        return storage.getAll();
    }

    public void deleteFilm(Film film) {
        storage.deleteFilm(film);
    }

    public Film getFilm(@PathVariable Long id) {
        return storage.getFilm(id);
    }

    public Film setLike(Long filmId, Long userId) {
        Film film = storage.getFilm(filmId);
        film.setLike(userId);
        return film;
    }

    public Film deleteLike(Long id, Long userId) {
        Film film = storage.getFilm(id);
        film.deletLike(userId);
        return film;
    }

    public Collection<Film> getTopTen() {
        return storage.getAll().stream().sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size()).limit(10).collect(Collectors.toList());
    }
}
