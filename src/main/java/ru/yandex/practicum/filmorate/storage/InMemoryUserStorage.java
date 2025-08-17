package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);

    @Override
    public User create(@Valid User user) {
        log.info("Start to create user {}", user.getName());
        user.setId(newId());
        users.put(user.getId(), user);
        log.debug("User was created with id = {}", user.getId());
        return user;
    }

    @Override
    public User update(@Valid User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("No user with id = {}", user.getId());
            throw new NoCandidatesFoundException("Can not find user by id = " + user.getId());
        }
        users.put(user.getId(), user);
        log.debug("User with id = {} was updated", user.getId());
        return user;
    }

    @Override
    public User getUser(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new NoCandidatesFoundException("Пользователь с id=" + id + " не найден.");
        }
        return user;
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public void deleteUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NoCandidatesFoundException("Пользователь с id=" + user.getId() + " не найден.");
        }
        users.remove(user);
    }

    private Long newId() {
        return users.keySet().stream().mapToLong(id -> id).max().orElse(0) + 1;
    }

}
