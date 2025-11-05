package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, GenreDbStorage.class, LikesDbStorage.class, MpaDbStorage.class,
        FilmRowMapper.class, Validator.class})
class FilmDbStorageTest {
    private final FilmDbStorage filmStorage;

    private Film generateFilm() {
        Film film = new Film();
        film.setName("Name " + Math.random());
        film.setDescription("description");
        film.setDuration(200);
        film.setReleaseDate(LocalDate.now());
        Mpa mpa = new Mpa();
        mpa.setId(1L);
        film.setMpa(mpa);
        film.setGenres(new ArrayList<>());
        film.setLikes(new HashSet<>());
        return film;
    }

    @Test
    public void checkCreateAndFindFilmById() throws InternalServerException {
        Film createdFilm = filmStorage.add(generateFilm());
        Film filmOptional = filmStorage.getFilm(createdFilm.getId());

        assertThat(filmOptional).isNotNull();
    }


    @Test
    public void checkUpdateFilm() throws InternalServerException {
        Film film = filmStorage.add(generateFilm());
        Film filmtoUpdete = generateFilm();
        filmtoUpdete.setId(film.getId());
        filmStorage.update(filmtoUpdete);

        Film actualFilm = filmStorage.getFilm(filmtoUpdete.getId());
        assertNotEquals(actualFilm.getName(), film.getName(), "Have not be the same");
    }

    @Test
    public void checkDeleteFilm() throws InternalServerException {
        Film createdFilm = filmStorage.add(generateFilm());
        filmStorage.deleteFilm(createdFilm);
        Film deletedFilm = filmStorage.getFilm(createdFilm.getId());

        assertThat(filmStorage.getAll().size()).isEqualTo(0);

    }


    @Test
    public void checkFindAllFilms() throws InternalServerException {
        filmStorage.add(generateFilm());
        filmStorage.add(generateFilm());
        filmStorage.add(generateFilm());

        List<Film> films = filmStorage.getAll();

        assertEquals(3, films.size(), "Вернулись не все фильмы");
    }


    @Test
    public void checkGetTop() throws InternalServerException {
        filmStorage.add(generateFilm());
        filmStorage.add(generateFilm());
        filmStorage.add(generateFilm());

        Collection<Film> films = filmStorage.getTop(2);

        assertEquals(2, films.size(), "Have to be 2, not 3");
    }

}