package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreBdStorage {
    private static final String GET_GENRES = "SELECT * FROM film_genre f LEFT JOIN genre g ON f.genre_id = g.genre_id WHERE film_id = :filmId;";
    private final NamedParameterJdbcTemplate jdbc;

    public List<String> getGenres(Long filmId){
        Map<String, Long> film = Collections.singletonMap("filmId", filmId);

        List<String> genres = jdbc.query(GET_GENRES, film,
                (rs, rowNum) -> rs.getString("genre_name"));
        return genres;
    }
}
