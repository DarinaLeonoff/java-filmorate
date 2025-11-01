package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@Service
public class FilmService {
    private final FilmStorage storage;
    private final UserService userService;
    private final LikesServer likesService;
    private final GenreService genreService;

    public FilmService(
            @Qualifier("filmDbStorage") FilmStorage storage,
            UserService userService,
            LikesServer likesService, GenreService genreService) {
        this.storage = storage;
        this.userService = userService;
        this.likesService = likesService;
        this.genreService = genreService;
    }

    public FilmDto addFilm(NewFilmRequest request) throws InternalServerException {
        Film film = FilmMapper.mapToFilm(request);
        film = storage.add(film);
        return FilmMapper.mapToDto(film, likesService, genreService);
    }

    public FilmDto update(Long id, UpdateFilmRequest request) throws InternalServerException {
        Film film = storage.getFilm(id);
        storage.update(film);
        System.out.println(film.getRatingId());
        return FilmMapper.mapToDto(film, likesService, genreService);
    }

    public Collection<FilmDto> getAll() {
        return storage.getAll().stream().map(f -> FilmMapper.mapToDto(f, likesService, genreService)).toList();
    }

    public void deleteFilm(Film film) {
        storage.deleteFilm(film);
    }

    public FilmDto getFilm(@PathVariable Long id) {
        return FilmMapper.mapToDto(storage.getFilm(id), likesService, genreService);
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
