package ru.yandex.practicum.filmorate.sevice;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private InMemoryFilmStorage storage;

    public FilmService(InMemoryFilmStorage storage) {
        this.storage = storage;
    }

    public Film addLike(Film film, Long userId) {
        film.setLike(userId);
        return film;
    }

    public Film deleteLike(Film film, Long userId) {
        film.deletLike(userId);
        return film;
    }

    public Collection<Film> getTopTen() {
        return storage.getAll().stream().sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size()).limit(10).collect(Collectors.toList());
    }
}
