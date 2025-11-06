package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class LikesDbStorage {
    private final NamedParameterJdbcTemplate jdbc;

    private static final String INSERT_LIKE = "INSERT INTO likes (film_id, user_id) VALUES (:filmId, :userId);";
    private static final String GET_FILM_LIKES = "SELECT * FROM likes WHERE film_id = (:filmId);";
    private static final String GET_LIKES_FOR_LIST = "SELECT * FROM likes WHERE film_id IN (:films);";
    private static final String GET_LIKES_COUNT_FOR_LIST = "SELECT film_id, COUNT(user_id) FROM likes WHERE film_id IN (:films) GROUP BY film_id;";
    private static final String GET_FILM_LIKES_COUNT = "SELECT COUNT(user_id) FROM likes WHERE film_id = (:filmId);";
    private static final String DELETE_LIKE = "DELETE FROM likes WHERE film_id = (:filmId) AND user_id = (:userId);";

    public void setLike(Long filmId, Long userId) {
        jdbc.batchUpdate(INSERT_LIKE, new SqlParameterSource[]{new MapSqlParameterSource("filmId", filmId).addValue("userId", userId)});
    }

    public Set<Long> getLikes(Long filmId) {
        Map<String, Long> film = Collections.singletonMap("filmId", filmId);

        return new HashSet<Long>(jdbc.query(GET_FILM_LIKES, film, (rs, rowNum) -> rs.getLong("user_id")));
    }

    public Map<Long, Set<Long>> getLikesForList(List<Long> films) {
        Map<Long, Set<Long>> result = new HashMap<>();
        Map<String, List<Long>> param = Collections.singletonMap("films", films);
        jdbc.query(GET_LIKES_FOR_LIST, param, (rs, rowNum) -> {
            Long id = rs.getLong("film_id");
            Set<Long> likes = result.get(id);
            if (likes == null) {
                likes = new HashSet<>();
            }
            likes.add(rs.getLong("user_id"));
            result.put(id, likes);
            return id;
        });
        return result;
    }

    public Map<Long, Integer> getLikesCountForList(List<Long> films) {
        Map<Long, Integer> result = new HashMap<>();
        Map<String, List<Long>> param = Collections.singletonMap("films", films);
        jdbc.query(GET_LIKES_COUNT_FOR_LIST, param, (rs, rowNum) -> {
            Long id = rs.getLong("film_id");
            int likes = rs.getInt("COUNT(user_id)");
            result.putIfAbsent(id, likes);
            return id;
        });
        return result;
    }

    public int getLikesCount(Long filmId) {
        Map<String, Long> film = Collections.singletonMap("filmId", filmId);
        return jdbc.queryForObject(GET_FILM_LIKES_COUNT, film, Integer.class);
    }

    public Set<Long> deleteLike(Long filmId, Long userId) {
        jdbc.batchUpdate(DELETE_LIKE, new SqlParameterSource[]{new MapSqlParameterSource("filmId", filmId).addValue("userId", userId)});
        return getLikes(filmId);
    }

}
