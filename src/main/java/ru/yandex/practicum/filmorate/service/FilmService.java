package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class FilmService {
    private final FilmStorage storage;
    private final UserService userService;
    private final LikesService likesService;
    private final GenreService genreService;
    private final RatingService ratingService;

    public FilmService(
            @Qualifier("filmDbStorage") FilmStorage storage,
            UserService userService,
            LikesService likesService, GenreService genreService, RatingService ratingService) {
        this.storage = storage;
        this.userService = userService;
        this.likesService = likesService;
        this.genreService = genreService;
        this.ratingService = ratingService;
    }

    public FilmDto addFilm(NewFilmRequest request) throws InternalServerException {
        Film film = FilmMapper.mapToFilm(request);
        ratingService.validateMpa(film.getRating());
        film = storage.add(film);
        genreService.setGenres(film.getId(), request.getGenres());
        return FilmMapper.mapToDto(film, likesService, genreService, ratingService);
    }

    public FilmDto update(Long id, UpdateFilmRequest request) throws InternalServerException {
        Film film = storage.getFilm(id);
        storage.update(film);
        System.out.println(film.getRating());
        return FilmMapper.mapToDto(film, likesService, genreService, ratingService);
    }

    public Collection<FilmDto> getAll() {
        return storage.getAll().stream().map(f -> FilmMapper.mapToDto(f, likesService, genreService, ratingService)).toList();
    }

    public void deleteFilm(Film film) {
        storage.deleteFilm(film);
    }

    public FilmDto getFilm(@PathVariable Long id) {
        return FilmMapper.mapToDto(storage.getFilm(id), likesService, genreService, ratingService);
    }
//
//    public Collection<Film> getTop(int count) {
//        return storage.getAll().stream().sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size()).limit(count).collect(Collectors.toList());
//    }
}
