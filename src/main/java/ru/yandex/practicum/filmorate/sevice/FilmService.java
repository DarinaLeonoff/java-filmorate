package ru.yandex.practicum.filmorate.sevice;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

@Service
public class FilmService {
    public Film addLike(Film film, Long userId) {
        film.setLike(userId);
        return film;
    }
    public Film deleteLike(Film film, Long userId){
        film.deletLike(userId);
        return film;
    }
//    public Collection<Film> getTopTen(){}
}
