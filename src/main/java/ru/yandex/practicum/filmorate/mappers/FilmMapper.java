package ru.yandex.practicum.filmorate.mappers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.LikesServer;

@Slf4j
public class FilmMapper {


   public static FilmDto mapToDto(Film film, LikesServer likesServer, GenreService genreService){
       FilmDto dto = new FilmDto();
       dto.setId(film.getId());
       dto.setName(film.getName());
       dto.setDescription(film.getDescription());
       dto.setDuration(film.getDuration());
       dto.setReleaseDate(film.getReleaseDate());
       dto.setGenres(genreService.getGenres(film.getId()));
       log.info("Adding likes to dto");
       dto.setLikes(likesServer.getLikesCount(film.getId()));
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
