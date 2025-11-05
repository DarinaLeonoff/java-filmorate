package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film add(Film film) throws InternalServerException;

    Film update(Film film) throws InternalServerException;

    Collection<Film> getAll();

    Film getFilm(Long id);

    void deleteFilm(Film film);

    Collection<Film> getTop(int count);
}
