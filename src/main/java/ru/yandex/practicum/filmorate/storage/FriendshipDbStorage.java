package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;

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
    private static final String GET_COMMON_FRIENDS_ID = "SELECT f1.friend_id " +
            "FROM friendship f1 " +
            "INNER JOIN friendship f2 " +
            "ON f1.friend_id = f2.friend_id " +
            "WHERE f1.user_id = :userId " +
            "AND f2.user_id = :friendId";

    private static final String SET_NEW_FRIENDSHIP = "INSERT INTO friendship (user_id, friend_id) VALUES (:userId, :friendId);";
    private static final String DELETE_FRIEND = "DELETE FROM friendship WHERE user_id = :userId AND friend_id = :friendId;";

    public Friendship getFriendsList(Long id) {
        List<Long> friends = getFriendsId(id, GET_FRIENDS_ID);
        Friendship friendship = new Friendship();
        for (Long friendId : friends) {
            friendship.setFriend(friendId);
        }
        return friendship;
    }

    public Friendship getCommonFriendsList(Long id, Long friendId) {
        Map<String, Long> params = new HashMap<>();
        params.put("userId", id);
        params.put("friendId", friendId);
        List<Long> userFriends = jdbc.query(GET_COMMON_FRIENDS_ID, params, (rs, rowNum) -> rs.getLong("friend_id"));
        Friendship friendship = new Friendship();
        for (Long friend : userFriends) {
            friendship.setFriend(friend);
        }
        return friendship;
    }

    public Friendship addFriend(Long userId, Long friendId) {
        SqlParameterSource[] batch = new SqlParameterSource[]{
                new MapSqlParameterSource("userId", userId).addValue("friendId", friendId)
        };

        jdbc.batchUpdate(SET_NEW_FRIENDSHIP, batch);
        return getFriendsList(friendId);
    }

    public Friendship deleteFriend(Long userId, Long friendId) {
        SqlParameterSource[] batch = new SqlParameterSource[]{
                new MapSqlParameterSource("userId", userId).addValue("friendId", friendId),
                new MapSqlParameterSource("userId", friendId).addValue("friendId", userId)
        };
        jdbc.batchUpdate(DELETE_FRIEND, batch);
        return getFriendsList(userId);
    }

    private List<Long> getFriendsId(Long id, String query) {
        Map<String, Long> params = Collections.singletonMap("userId", id);
        return jdbc.query(
                query,
                params,
                (rs, rowNum) -> rs.getLong("friend_id"));
    }
}