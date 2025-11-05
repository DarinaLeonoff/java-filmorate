package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDbStorage storage;

    public List<GenreDto> getGenres(Long filmId) {
        return storage.getGenres(filmId);
    }

    public List<GenreDto> getAllGenres() {
        return storage.getAllGenres();
    }
    public Map<Long, List<GenreDto>> getGenresForList(List<Long> films) {
        return storage.getGenresForList(films);
    }

    public GenreDto getGenre(Long id) {
        return storage.getGenre(id);
    }

    public void setGenres(Long filmId, List<Genre> genres) {
        storage.setGenres(filmId, genres);
    }

}
