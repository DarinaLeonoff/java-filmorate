package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.RatingDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingDbStorage storage;

    public MpaDto getFilmRating(Long filmId){
        return storage.getFilmRating(filmId);
    }

    public List<MpaDto> getAllRatings(){
        return storage.getAllRatings();
    }

    public MpaDto getById(Long id){
        validateMpa(id);
        return storage.getById(id);
    }

    public boolean validateMpa(Long id){
        return storage.isIdValid(id);
    }
}
