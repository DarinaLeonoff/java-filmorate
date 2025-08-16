package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public User getById(Long id) {
        return userStorage.getUser(id);
    }

    public User addFriend(Long id, Long friendId) {
        if (id.equals(friendId)) {
            throw new IllegalArgumentException("Нельзя добавить себя в друзья");
        }
        User user1 = userStorage.getUser(id);
        User user2 = userStorage.getUser(friendId);

        boolean added1 = user1.setFriend(user2.getId());
        boolean added2 = user2.setFriend(user1.getId());

        if (!added1 || !added2) {
            log.warn("Попытка повторно подружить пользователей {} и {}", id, friendId);
            throw new AlreadyExistsException("Пользователи уже являются друзьями");
        }

        log.info("Users become friends.");
        return user1;
    }

    public User deleteFriend(Long id, Long friendId) {
        User user1 = userStorage.getUser(id);
        User user2 = userStorage.getUser(friendId);

        boolean removed1 = user1.deleteFriend(user2.getId());
        boolean removed2 = user2.deleteFriend(user1.getId());

        if (removed1 && removed2) {
            return user1;
        }
        throw new NoCandidatesFoundException("Юзер не найден в списке друзей.");
    }

    public Collection<Long> mutualFriends(Long id, Long otherId) {
        User user1 = userStorage.getUser(id);
        User user2 = userStorage.getUser(otherId);

        Set<Long> f1 = user1.getFriends().stream().collect(Collectors.toSet());

        return user2.getFriends().stream().filter(f1::contains).collect(Collectors.toList());
    }

    public Collection<Long> getFriends(Long id) {
        return userStorage.getUser(id).getFriends();
    }
}
