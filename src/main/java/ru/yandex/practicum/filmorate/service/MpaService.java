package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaDbStorage storage;

    public Mpa getFilmRating(Long filmId) {
        return storage.getFilmRating(filmId);
    }

    public List<Mpa> getAllRatings() {
        return storage.getAllRatings();
    }

    public Mpa getById(Long id) {
        validateMpa(id);
        return storage.getById(id);
    }

    public boolean validateMpa(Long id) {
        return storage.isIdValid(id);
    }
}
