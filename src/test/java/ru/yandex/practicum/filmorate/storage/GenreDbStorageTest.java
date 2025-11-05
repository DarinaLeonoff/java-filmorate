package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.*;

import javax.xml.validation.Validator;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, FilmRowMapper.class, FilmService.class,
        GenreService.class, GenreDbStorage.class,
        LikesDbStorage.class, LikesService. class,
        MpaDbStorage.class, MpaService.class,
        UserService.class, UserDbStorage.class, UserRowMapper.class})
public class GenreDbStorageTest {
    private final FilmService filmService;
    private final GenreDbStorage genreDbStorage;

    private NewFilmRequest generateNewFilmRequest(int count){
        NewFilmRequest request = new NewFilmRequest();
        request.setName("Интерстеллар");
        request.setDescription("Захватывающий научно-фантастический фильм");
        request.setDuration(169);
        request.setReleaseDate(LocalDate.of(2014, 11, 7));
        Mpa mpa = new Mpa();
        mpa.setId(1l);
        request.setMpa(mpa);
        List<Genre> genres = new ArrayList<>();
        for (int i = 1; i <=count; i++) {
            Genre genre = new Genre();
            genre.setId((long)i);
            genres.add(genre);
        }
        request.setGenres(genres);

        return request;
    }

    @Test
    public void checkSetAndGetFilmGenres() throws InternalServerException {
        int count = 3;
        FilmDto film = filmService.addFilm(generateNewFilmRequest(count));
        log.info("new film id = {}", film.getId());
        List<GenreDto> genres = genreDbStorage.getGenres(film.getId());
        log.info("Genres: "+ genres);
        assertEquals(count, genres.size(), "Have to be "+count);
    }

    @Test
    public void checkAllGenres(){
        List<GenreDto> allGenres = genreDbStorage.getAllGenres();
        assertEquals(6, allGenres.size(), "Have to be 6");
    }

    @Test
    public void checkGetGenreById(){
        Long id = 1l;
        String name = "Комедия";
        GenreDto genre = genreDbStorage.getGenre(id);
        assertEquals(id, genre.getId(), "Have to be " + id);
        assertEquals(name, genre.getName(), "Have to be " + name);
    }

    @Test
    public void checkGetGenresFirListOfFilms() throws InternalServerException {
        FilmDto film1 = filmService.addFilm(generateNewFilmRequest(1));
        FilmDto film2 = filmService.addFilm(generateNewFilmRequest(2));
        FilmDto film3 = filmService.addFilm(generateNewFilmRequest(3));

        List<Long> ids = List.of(film1.getId(), film2.getId(), film3.getId());

        Map<Long, List<GenreDto>> map = genreDbStorage.getGenresForList(ids);

        assertNotNull(map.get(film1.getId()), "Not found film1 in map");
        assertNotNull(map.get(film2.getId()), "Not found film2 in map");
        assertNotNull(map.get(film3.getId()), "Not found film3 in map");

        assertEquals(1, map.get(film1.getId()).size(), "Have to be 1");
        assertEquals(2, map.get(film2.getId()).size(), "Have to be 2");
        assertEquals(3, map.get(film3.getId()).size(), "Have to be 3");
    }

}
