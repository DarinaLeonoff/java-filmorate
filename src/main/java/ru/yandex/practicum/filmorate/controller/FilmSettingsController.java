package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilmSettingsController {
    private final GenreService genreService;
    private final MpaService mpaService;

    @GetMapping("/genres")
    public List<GenreDto> getAllGenres(){
        return genreService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public GenreDto getGenres(@PathVariable Long id){
        return genreService.getGenre(id);
    }

    @GetMapping("/mpa")
    public List<MpaDto> getAllratings(){
        return mpaService.getAllRatings();
    }

    @GetMapping("/mpa/{id}")
    public MpaDto getMpaById(@PathVariable Long id){
        return mpaService.getById(id);
    }
}
