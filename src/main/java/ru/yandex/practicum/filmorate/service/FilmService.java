package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage storage;
    //private final UserService userService;
    public FilmService(
            @Qualifier("filmDbStorage") FilmStorage storage
            //UserService userService
    ) {
        this.storage = storage;
        //this.userService = userService;
    }

    public FilmDto addFilm(NewFilmRequest request) throws InternalServerException {
        Film film = FilmMapper.mapToFilm(request);
        film = storage.add(film);
        return FilmMapper.mapToDto(film);
    }

//    public Film update(UpdateFilmRequest request) throws InternalServerException {
//        return storage.update(film);
//    }

    public Collection<Film> getAll() {
        return storage.getAll();
    }

    public void deleteFilm(Film film) {
        storage.deleteFilm(film);
    }

    public Film getFilm(@PathVariable Long id) {
        return storage.getFilm(id);
    }

//    public Film setLike(Long filmId, Long userId) {
//        User user = userService.getById(userId);
//        if (user == null) {
//            throw new NoCandidatesFoundException("Юзер с id=" + userId + " не найден.");
//        }
//        Film film = storage.getFilm(filmId);
//        film.setLike(userId);
//        return film;
//    }
//
//    public Film deleteLike(Long id, Long userId) {
//        Film film = storage.getFilm(id);
//        film.deleteLike(userId);
//        return film;
//    }
//
//    public Collection<Film> getTop(int count) {
//        return storage.getAll().stream().sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size()).limit(count).collect(Collectors.toList());
//    }
}
