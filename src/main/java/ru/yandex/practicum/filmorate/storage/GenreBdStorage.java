package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GenreBdStorage {
    private static final String GET_GENRES = "SELECT * FROM film_genre f LEFT JOIN genre g ON f.genre_id = g.genre_id WHERE film_id = :filmId ORDER BY genre_id ASC ;";
    private static final String GET_ALL_GENRES = "SELECT * FROM genre ORDER BY genre_id ASC;";
    private static final String GET_GENRE = "SELECT genre_name FROM genre WHERE genre_id = :genreId;";
    private static final String SET_GENRES = "INSERT INTO film_genre(film_id, genre_id) VALUES(:filmId, :genreId);";
    private final NamedParameterJdbcTemplate jdbc;

    public List<Genre> getGenres(Long filmId) {
        Map<String, Long> film = Collections.singletonMap("filmId", filmId);

        List<Genre> genres = jdbc.query(GET_GENRES, film,
                (rs, rowNum) -> {
                    Genre genre = new Genre();
                    genre.setId(rs.getLong("genre_id"));
                    return genre;
                });
        log.info("Genres: {}", genres);
        return genres;
    }

    public List<GenreDto> getAllGenres() {
        return jdbc.query(GET_ALL_GENRES,
                (rs, rowNum) -> {
                    Genre genre = new Genre();
                    genre.setId(rs.getLong("genre_id"));
                    return GenreMapper.mapToDto(genre);
                });
    }

    public GenreDto getGenre(Long id) {
        isIdValid(id);
        Map<String, Long> genreId = Collections.singletonMap("genreId", id);
        Genre genre = jdbc.queryForObject(GET_GENRE, genreId, (rs, rowNum) -> {
            Genre newGenre = new Genre();
            newGenre.setId(id);
//            newGenre.setName(rs.getString("genre_name"));
            return newGenre;
        });

        return GenreMapper.mapToDto(genre);
    }

    public void setGenres(Long filmId, Set<Genre> genres) {
        for (Genre genre : genres) {
            Long genreId = genre.getId();
            isIdValid(genreId);
            jdbc.batchUpdate(SET_GENRES, new SqlParameterSource[]{
                    new MapSqlParameterSource("filmId", filmId).addValue("genreId", genreId)
            });
        }
    }

    private void isIdValid(Long id) {
        List<Long> genres = jdbc.query(GET_ALL_GENRES,
                (rs, rowNum) -> rs.getLong("genre_id"));
        if (!genres.contains(id)) {
            throw new NoCandidatesFoundException("Жанр не найден");
        }
    }

}
