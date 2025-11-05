package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
@Qualifier("filmDbStorage")
public class FilmDbStorage extends BaseDbStorage<Film> implements FilmStorage {
    private static final String INSERT_QUERY = "INSERT INTO films (name, description, duration, release_date, rating_id) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, duration = ?, release_date = ?, rating_id = ? WHERE film_id = ?;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM films LEFT JOIN mpa ON films.rating_id = mpa.rating_id;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM films WHERE film_id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM films WHERE film_id = ?;";
    private static final String GET_TOP =
            "SELECT f.*, COALESCE(l.film_likes, 0) as film_likes " +
                    "FROM films f " +
                    "LEFT JOIN (" +
                    "SELECT film_id, COUNT(user_id) AS film_likes " +
                    "FROM likes " +
                    "GROUP BY film_id" +
                    ") l ON f.film_id = l.film_id " +
                    "ORDER BY film_likes DESC " +
                    "LIMIT ?;";


    private final Validator validator;

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper, Validator validator) {
        super(jdbc, mapper);
        this.validator = validator;
    }

    @Override
    public Film add(Film film) throws InternalServerException {
        validateFilm(film);
        Long id = insert(INSERT_QUERY, film.getName(), film.getDescription(), film.getDuration(),
                film.getReleaseDate(), film.getMpa().getId());
        film.setId(id);
        log.info("New film was added with id = {}", id);
        return film;
    }

    @Override
    public Film update(Film film) throws InternalServerException {
        validateFilm(film);
        update(UPDATE_QUERY, film.getName(), film.getDescription(), film.getDuration(), film.getReleaseDate(),
                film.getMpa().getId(), film.getId());
        return getFilm(film.getId());
    }

    @Override
    public List<Film> getAll() {
        return findMany(FIND_ALL_QUERY);
    }
    //find by name
    //find by year
    //find by rating

    @Override
    public Film getFilm(Long id) {
        Film film = findOne(FIND_BY_ID_QUERY, id).orElseThrow(() -> new NoCandidatesFoundException("Фильм не найден c id = " + id));
        return film;
    }

    @Override
    public void deleteFilm(Film film) {
        validateFilm(film);
        if (!delete(DELETE_QUERY, film.getId())) {
            throw new NoCandidatesFoundException("Фильм с id = " + film.getId() + " не был удален.");
        }
    }

    @Override
    public Collection<Film> getTop(int count) {
        return findMany(GET_TOP, count);
    }

    private void validateFilm(Film film) {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed", violations);
        }
    }
}
