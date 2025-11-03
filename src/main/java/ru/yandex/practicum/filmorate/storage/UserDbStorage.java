package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

@Repository
@Qualifier("userDbStorage")
public class UserDbStorage extends BaseDbStorage<User> implements UserStorage {
    private static final String INSERT_QUERY = "INSERT INTO users (name, email, birthday, login) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE users SET name = ?, email = ?, birthday = ?, login = ? WHERE " +
            "user_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE user_id = ?;";
    private final Validator validator;

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper, Validator validator) {
        super(jdbc, mapper);
        this.validator = validator;
    }

    @Override
    public User create(User user) throws InternalServerException {
        validateUser(user);
        Long id = insert(INSERT_QUERY, user.getName(), user.getEmail(), user.getBirthday(), user.getLogin());
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) throws InternalServerException {
        validateUser(user);
        update(UPDATE_QUERY, user.getName(), user.getEmail(), user.getBirthday(), user.getLogin(), user.getId());
        return user;
    }

    @Override
    public List<User> getAll() {
        return findMany(FIND_ALL_QUERY);
    }

    //find by name

    @Override
    public User getUser(Long id) {
        return findOne(FIND_BY_ID_QUERY, id).orElseThrow(() -> new NoCandidatesFoundException("Пользователь не найден."));
    }

    @Override
    public void deleteUser(User user) {
        validateUser(user);
        if (!delete(DELETE_QUERY, user.getId())) {
            throw new NoCandidatesFoundException("Пользователь с id = " + user.getId() + " не был удален.");
        }
    }

    private void validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed", violations);
        }
    }
}
