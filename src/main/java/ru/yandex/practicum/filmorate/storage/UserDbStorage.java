package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public class UserDbStorage extends BaseDbStorage<User>{
    private static final String INSERT_QUERY = "INSERT INTO users (name, email, birthday, login) VALUES (?, ?, ?, ?) returning user_id;";
    private static final String UPDATE_QUERY = "UPDATE users SET name = ?, email = ?, birthday = ?, login = ? WHERE " +
            "user_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE user_id = ?;";

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public User createUser(User user) throws InternalServerException {
        Long id = insert(INSERT_QUERY, user.getName(), user.getEmail(), user.getBirthday(), user.getLogin());
        user.setId(id);
        return user;
    }


    public User update(User user) throws InternalServerException {
        update(UPDATE_QUERY, user.getName(), user.getEmail(), user.getBirthday(), user.getLogin(), user.getId());
        return user;
    }

    public List<User> getAll() {
        return findMany(FIND_ALL_QUERY);
    }

    //find by name
    //find by email

    public Optional<User> getUser(Long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    public void deleteUser(User user) {
        if(!delete(DELETE_QUERY, user.getId())){
            throw new NoCandidatesFoundException("Пользователь с id = " + user.getId()+" не был удален.");
        }
    }
}
