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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage storage;
    private final UserService userService;
    private final LikesService likesService;
    private final GenreService genreService;
    private final MpaService mpaService;

    public FilmService(
            @Qualifier("filmDbStorage") FilmStorage storage,
            UserService userService,
            LikesService likesService, GenreService genreService, MpaService mpaService) {
        this.storage = storage;
        this.userService = userService;
        this.likesService = likesService;
        this.genreService = genreService;
        this.mpaService = mpaService;
    }

    public FilmDto addFilm(NewFilmRequest request) throws InternalServerException {
        mpaService.validateMpa(request.getMpa().getId());
        Film film = FilmMapper.mapToFilm(request);
        film = storage.add(film);
        genreService.setGenres(film.getId(), request.getGenres());
        return FilmMapper.mapToDto(film, likesService, genreService, mpaService);
    }

    public FilmDto update(UpdateFilmRequest request) throws InternalServerException {
        log.info("Updating film with id = {}", request.getId());
        mpaService.validateMpa(request.getMpa().getId());
        Film film = storage.getFilm(request.getId());
        film = FilmMapper.updateFilm(film, request);
        storage.update(film);
        System.out.println(film.getMpa());
        return FilmMapper.mapToDto(film, likesService, genreService, mpaService);
    }

    public Collection<FilmDto> getAll() {
        return storage.getAll().stream().map(f -> FilmMapper.mapToDto(f, likesService, genreService, mpaService)).toList();
    }

    public void deleteFilm(Film film) {
        storage.deleteFilm(film);
    }

    public FilmDto getFilm(@PathVariable Long id) {
        return FilmMapper.mapToDto(storage.getFilm(id), likesService, genreService, mpaService);
    }

    public List<FilmDto> getTop(int count) {
        List<FilmDto> films = new ArrayList<>();
        for (Film film : storage.getAll()) {
            films.add(FilmMapper.mapToDto(film, likesService, genreService, mpaService));
        }
        return films.stream().sorted(Comparator.comparingInt(FilmDto::getLikes).reversed()).limit(count).toList();
    }
}
