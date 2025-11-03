package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User create(User user) throws InternalServerException;

    User update(User user) throws InternalServerException;

    User getUser(Long id);

    Collection<User> getAll();

    void deleteUser(User user);
}
