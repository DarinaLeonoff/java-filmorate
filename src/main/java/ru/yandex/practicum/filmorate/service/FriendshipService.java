package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipDbStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipDbStorage friendshipDbStorage;
    private final UserService userService;

    public List<User> getFriends(Long id) {
        if (!isPresent(id)) {
            throw new NoCandidatesFoundException("Пользователь не найден.");
        }
        return friendsToUserList(friendshipDbStorage.getFriendsList(id));
    }

    public List<User> getCommonFriends(Long id, Long friendId) {
        if (!isPresent(id) || !isPresent(friendId)) {
            throw new NoCandidatesFoundException("Пользователь не найден.");
        }
        return friendsToUserList(friendshipDbStorage.getCommonFriendsList(id, friendId));
    }

    public List<User> addFriend(Long userId, Long friendId) {
        if (!isPresent(userId))
            throw new NoCandidatesFoundException("Невозможно подружиться. " + userId + " - не существует");
        if (!isPresent(friendId))
            throw new NoCandidatesFoundException("Невозможно подружиться. " + friendId + " - не существует");
        if (isFriends(userId, friendId)) throw new NoCandidatesFoundException("Пользователи уже дружат.");
        return friendsToUserList(friendshipDbStorage.addFriend(userId, friendId));
    }

    public List<User> deleteFriend(Long userId, Long friendId) {
        if (!isPresent(userId))
            throw new NoCandidatesFoundException("Невозможно удалить друга. " + userId + " - не существует ");
        if (!isPresent(friendId))
            throw new NoCandidatesFoundException("Невозможно удалить друга. " + friendId + " - не существует");
        if (!isFriends(userId, friendId)) {
            log.debug("Попытка удалить из друзей пользователь, оторый не является другом.");
        }
        return friendsToUserList(friendshipDbStorage.deleteFriend(userId, friendId));
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
        return friendshipDbStorage.getFriendsList(id).contains(friendId);
    }

    private List<User> friendsToUserList(List<Long> friends){
        List<User> users = new ArrayList<>();
        for(Long friend : friends){
            users.add(userService.getById(friend));
        }
        return users;
    }
}
