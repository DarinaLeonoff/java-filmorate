package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FriendshipDbStorage {
    private final JdbcTemplate jdbc;
    private static final String GET_FRIENDS_ID = "SELECT friend_id FROM friendship WHERE user_id = ?;";


    public Friendship getFriendsList(Long id){
        log.info("Start getting friends.");
        Friendship friendship = new Friendship(id, jdbc.queryForList(GET_FRIENDS_ID, new Object[]{id},
                Long.class));
        return friendship;
    }
}
