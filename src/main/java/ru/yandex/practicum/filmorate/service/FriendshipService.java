package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.FriendshipDbStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipDbStorage friendshipDbStorage;
    private final UserService userService;

    public Friendship getFriends(Long id) {
        if (!isPresent(id)) {
            throw new NoCandidatesFoundException("Пользователь не найден.");
        }
        return friendshipDbStorage.getFriendsList(id);
    }

    public Friendship addFriend(Long userId, Long friendId) {
        if (!isPresent(userId))
            throw new NoCandidatesFoundException("Невозможно подружиться. " + userId + " - не существует");
        if (!isPresent(friendId))
            throw new NoCandidatesFoundException("Невозможно подружиться. " + friendId + " - не существует");
        if (isFriends(userId, friendId)) throw new NoCandidatesFoundException("Пользователи уже дружат.");
        return friendshipDbStorage.addFriend(userId, friendId);
    }

    public Friendship deleteFriend(Long userId, Long friendId) {
        if (!isPresent(userId))
            throw new NoCandidatesFoundException("Невозможно удалить друга. " + userId + " - не существует ");
        if (!isPresent(friendId))
            throw new NoCandidatesFoundException("Невозможно удалить друга. " + friendId + " - не существует");
        if (!isFriends(userId, friendId)) {
            return friendshipDbStorage.getFriendsList(userId);
        }
        return friendshipDbStorage.deleteFriend(userId, friendId);
    }


    private boolean isPresent(Long id) {
        log.info("Check user {} existence", id);
        try {
            userService.getById(id);
            return true;
        } catch (RuntimeException e) {
            log.warn("User with is {} not found", id);
            throw new NoCandidatesFoundException("Пользователь не найден!");
        }
    }

    private boolean isFriends(Long id, Long friendId) {
        return friendshipDbStorage.getFriendsList(id).getFriends().contains(new Friendship.Friend(friendId));
    }
}
