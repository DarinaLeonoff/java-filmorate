package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FriendshipDbStorage {
    private final NamedParameterJdbcTemplate jdbc;
    private static final String GET_FRIENDS_ID = "SELECT friend_id FROM friendship WHERE user_id = :userId;";
    private static final String SET_NEW_FRIENDSHIP = "INSERT INTO friendship (user_id, friend_id) VALUES (:userId, :friendId);";
    private static final String DELETE_FRIEND = "DELETE FROM friendship WHERE user_id = :userId AND friend_id = :friendId;";

    public Friendship getFriendsList(Long id){
        Map<String, Long> params = Collections.singletonMap("userId", id);

        List<Long> friends = jdbc.query(
                GET_FRIENDS_ID,
                params,
                (rs, rowNum) -> rs.getLong("friend_id")
        );
        return new Friendship(id, friends);
    }

    public Friendship addFriend(Long userId, Long friendId){
        SqlParameterSource[] batch = new SqlParameterSource[]{
                new MapSqlParameterSource("userId", userId).addValue("friendId", friendId),
                new MapSqlParameterSource("userId", friendId).addValue("friendId", userId)
        };

        jdbc.batchUpdate(SET_NEW_FRIENDSHIP, batch);
        return getFriendsList(userId);
    }

    public Friendship deleteFriend(Long userId, Long friendId){
        SqlParameterSource[] batch = new SqlParameterSource[]{
                new MapSqlParameterSource("userId", userId).addValue("friendId", friendId),
                new MapSqlParameterSource("userId", friendId).addValue("friendId", userId)
        };
        jdbc.batchUpdate(DELETE_FRIEND, batch);
        return getFriendsList(userId);
    }
}