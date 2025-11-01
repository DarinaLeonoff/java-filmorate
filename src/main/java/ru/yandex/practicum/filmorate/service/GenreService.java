package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.GenreBdStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreBdStorage storage;

    public List<String> getGenres(Long filmId){
       return storage.getGenres(filmId);
    }
}
