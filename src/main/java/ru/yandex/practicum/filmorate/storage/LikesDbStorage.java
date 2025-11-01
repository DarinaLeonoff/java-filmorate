package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmLikes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class LikesDbStorage {
    private final NamedParameterJdbcTemplate jdbc;

    private static final String INSERT_LIKE = "INSERT INTO likes (film_id, user_id) VALUES (:filmId, :userId);";
    private static final String GET_FILM_LIKES = "SELECT * FROM likes WHERE film_id = :filmId;";
    private static final String GET_FILM_LIKES_COUNT = "SELECT COUNT(user_id) FROM likes WHERE film_id = :filmId;";
    private static final String DELETE_LIKE = "DELETE FROM likes WHERE film_id = :filmId AND user_id = :userId;";
    public void setLike(Long filmId, Long userId){
        jdbc.batchUpdate(INSERT_LIKE, new SqlParameterSource[]{
                new MapSqlParameterSource("filmId", filmId).addValue("userId", userId)
        });
    }

    public FilmLikes getLikes(Long filmId){
        Map<String,Long> film = Collections.singletonMap("filmId", filmId);

        List<Long> likes = jdbc.query(GET_FILM_LIKES, film,
                (rs, rowNum) -> rs.getLong("user_id"));
        return new FilmLikes(filmId, likes);
    }

    public int getLikesCount(Long filmId){
        Map<String,Long> film = Collections.singletonMap("filmId", filmId);
        return jdbc.queryForObject(GET_FILM_LIKES_COUNT, film, Integer.class);
    }

    public FilmLikes deleteLike(Long filmId, Long userId){
        jdbc.batchUpdate(DELETE_LIKE, new SqlParameterSource[]{
                new MapSqlParameterSource("filmId", filmId).addValue("userId", userId)
        });
        return getLikes(filmId);
    }

}
