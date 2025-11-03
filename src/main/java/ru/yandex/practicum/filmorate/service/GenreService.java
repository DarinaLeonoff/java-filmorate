package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreBdStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreBdStorage storage;

    public List<Genre> getGenres(Long filmId){
       return storage.getGenres(filmId);
    }
    public List<GenreDto> getAllGenres(){
        return storage.getAllGenres();
    }
    public GenreDto getGenre(Long id){
        return storage.getGenre(id);
    }
    public void setGenres(Long filmId, Set<Genre> genres){
        storage.setGenres(filmId, genres);
    }

}
