package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.sevice.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private InMemoryUserStorage userStorage = new InMemoryUserStorage();
    private UserService userService = new UserService();
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public User create(@RequestBody User user) {
        return userStorage.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return userStorage.update(user);
    }

    @GetMapping
    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    //"users/{id}"

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userStorage.getUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        try {
            User user1 = userStorage.getUser(id);
            User user2 = userStorage.getUser(friendId);
            return userService.addFriend(user1, user2);
        } catch (NullPointerException e) {
            throw new NoCandidatesFoundException("Юзер с указанным Id не найден.");
        }
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        try {
            User user1 = userStorage.getUser(id);
            User user2 = userStorage.getUser(friendId);
            return userService.deleteFriend(user1, user2);
        } catch (NullPointerException e) {
            throw new NoCandidatesFoundException("Юзер с указанным Id не найден.");
        }
    }

    @GetMapping("/{id}/friends/common/{friendId}")
    public Collection<Long> getCommonFriends(@PathVariable Long id, @PathVariable Long friendId) {
        try {
            User user1 = userStorage.getUser(id);
            User user2 = userStorage.getUser(friendId);
            return userService.mutualFriends(user1, user2);
        } catch (NullPointerException e) {
            throw new NoCandidatesFoundException("Юзер с указанным Id не найден.");
        }
    }

    @GetMapping("/{id}/friends")
    public Collection<Long> getUserFriends(@PathVariable Long id) {
        try{
            return userStorage.getUser(id).getFriends();
        }catch (NullPointerException e){
        throw new NoCandidatesFoundException("Юзер с указанным Id не найден.");
    }
    }

}
