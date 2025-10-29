package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public class FilmDbStorage extends BaseDbStorage<Film>{
    private static final String INSERT_QUERY = "INSERT INTO films (name, description, duration, release_date, rating_id) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, duration = ?, release_date = ?, rating_id = ? WHERE film_id = ?;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM films;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM films WHERE film_id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM films WHERE film_id = ?;";
    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }


    public Film addFilm(Film film) throws InternalServerException {
        Long id = insert(INSERT_QUERY, film.getName(), film.getDescription(), film.getDuration(), film.getReleaseDate(), film.getRating());
        film.setId(id);
        return film;
    }


    public Film update(Film film) throws InternalServerException {
        update(UPDATE_QUERY, film.getName(), film.getDescription(), film.getDuration(), film.getReleaseDate(), film.getRating(), film.getId());
        return film;
    }


    public List<Film> getAll() {
        return findMany(FIND_ALL_QUERY);
    }
    //find by name
    //find by year
    //find by rating

    public Optional<Film> getFilm(Long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    public void deleteFilm(Film film) {
        if(!delete(DELETE_QUERY, film.getId())){
            throw new NoCandidatesFoundException("Фильм с id = " + film.getId() +" не был удален.");
        }
    }
}
