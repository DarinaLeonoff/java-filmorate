package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

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
        film.setMpa(mpaService.getById(film.getMpa().getId()));
        film = storage.add(film);
        genreService.setGenres(film.getId(), request.getGenres());
        return FilmMapper.mapToDto(film, likesService.getLikesCount(film.getId()), genreService.getGenres(film.getId()));
    }

    public FilmDto update(UpdateFilmRequest request) throws InternalServerException {
        mpaService.validateMpa(request.getMpa().getId());
        Film film = storage.getFilm(request.getId());
        film = FilmMapper.updateFilm(film, request);
        film = storage.update(film);
        film.setMpa(mpaService.getFilmRating(film.getMpa().getId()));
        Long id = film.getId();
        return FilmMapper.mapToDto(film, likesService.getLikesCount(id), genreService.getGenres(id));
    }

    public Collection<FilmDto> getAll() {
        Collection<Film> films = storage.getAll();
        return listToDto(films);
    }

    public void deleteFilm(Film film) {
        storage.deleteFilm(film);
    }

    public FilmDto getFilm(@PathVariable Long id) {
        Film film = storage.getFilm(id);
        film.setMpa(mpaService.getFilmRating(id));
        return FilmMapper.mapToDto(film, likesService.getLikesCount(id), genreService.getGenres(id));
    }

    public List<FilmDto> getTop(int count) {
        return listToDto(storage.getTop(count));
    }

    private List<FilmDto> listToDto(Collection<Film> films){
        List<Long> filmsId = films.stream().map(Film::getId).toList();
        Map<Long, List<GenreDto>> genres = genreService.getGenresForList(filmsId);
//        Map<Long, Set<Long>> likes = likesService.getLikesForList(filmsId);
        Map<Long, Integer> likes = likesService.getLikesCountForList(filmsId);
        return films.stream().map(f -> FilmMapper.mapToDto(f,
                likes.get(f.getId()) == null ? 0 : likes.get(f.getId()),
                genres.get(f.getId()) == null ? new ArrayList<>() : genres.get(f.getId())
        )).toList();
    }
}
