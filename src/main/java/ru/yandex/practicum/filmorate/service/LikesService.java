package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.FilmLikes;
import ru.yandex.practicum.filmorate.storage.LikesDbStorage;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesDbStorage storage;

    public void setLike(Long filmId, Long userId){
        if(!hasLike(filmId, userId)) {
            storage.setLike(filmId, userId);
        } else {
            throw new AlreadyExistsException("Данный пользователь уже поставил фильму лайк");
        }
    }

    public FilmLikes getLikes(Long filmId){
        return storage.getLikes(filmId);
    }

    public int getLikesCount(Long filmId){
        return storage.getLikesCount(filmId);
    }

    public FilmLikes deleteLike(Long filmId, Long userId){
        if(hasLike(filmId, userId)){
            return storage.deleteLike(filmId, userId);
        } else {
            throw new NoCandidatesFoundException("Пользователь не ставил лайк этому фильму");
        }
    }

    private boolean hasLike(Long filmId, Long userId){
        return getLikes(filmId).getUserId().contains(userId);
    }
}
