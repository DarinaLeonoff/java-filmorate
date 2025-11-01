package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FilmLikes;
import ru.yandex.practicum.filmorate.storage.LikesDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesServer {
    private final LikesDbStorage storage;

    public void setLike(Long filmId, Long userId){
        storage.setLike(filmId, userId);
    }

    public FilmLikes getLikes(Long filmId){
        return storage.getLikes(filmId);
    }

    public int getLikesCount(Long filmId){
        return storage.getLikesCount(filmId);
    }
}
