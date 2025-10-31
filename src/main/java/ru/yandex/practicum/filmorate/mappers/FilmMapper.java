package ru.yandex.practicum.filmorate.mappers;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmMapper {
   public static FilmDto mapToDto(Film film){
       FilmDto dto = new FilmDto();
       dto.setId(film.getId());
       dto.setName(film.getName());
       dto.setDescription(film.getDescription());
       dto.setDuration(film.getDuration());
       dto.setReleaseDate(film.getReleaseDate());
       dto.setRatingId(film.getRatingId());

       return dto;
   }

   public static Film mapToFilm(NewFilmRequest request){
       Film film = new Film();
       film.setName(request.getName());
       film.setDescription(request.getDescription());
       film.setDuration(request.getDuration());
       film.setReleaseDate(request.getReleaseDate());
       film.setRatingId(request.getRatingId());

       return film;
   }

   public static Film updateFilm(Film film, UpdateFilmRequest request){
       if(request.hasName()){
           film.setName(request.getName());
       }
       if(request.hasDescription()){
           film.setDescription(request.getDescription());
       }
       if(request.hasDuration()){
           film.setDuration(request.getDuration());
       }
       if(request.hasReleaseDate()){
           film.setReleaseDate(request.getReleaseDate());
       }
       if(request.hasRating()){
           film.setRatingId(request.getRatingId());
       }

       return film;
   }

}
