package ru.yandex.practicum.filmorate.mappers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.LikesService;
import ru.yandex.practicum.filmorate.service.RatingService;

@Slf4j
@RequiredArgsConstructor
public class FilmMapper {

   public static FilmDto mapToDto(Film film, LikesService likesService, GenreService genreService, RatingService ratingService){
       FilmDto dto = new FilmDto();
       Long id = film.getId();
       dto.setId(id);
       dto.setName(film.getName());
       dto.setDescription(film.getDescription());
       dto.setDuration(film.getDuration());
       dto.setReleaseDate(film.getReleaseDate());
       dto.setGenres(genreService.getGenres(id));
       dto.setLikes(likesService.getLikesCount(id));
       dto.setMpa(MpaMapper.mapToMpa(ratingService.getFilmRating(id)));
       log.info("new film dto: {}", dto);
       return dto;
   }

   public static Film mapToFilm(NewFilmRequest request){
       Film film = new Film();
       film.setName(request.getName());
       film.setDescription(request.getDescription());
       film.setDuration(request.getDuration());
       film.setReleaseDate(request.getReleaseDate());
       film.setMpa(MpaMapper.mpaGenerator(request.getMpa().getId()));
       film.setGenres(request.getGenres());
       log.info("Film was made successful.");
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
//       if(request.hasMpa()){
//           film.setMpa(request.getM);
//       }

       return film;
   }

}
