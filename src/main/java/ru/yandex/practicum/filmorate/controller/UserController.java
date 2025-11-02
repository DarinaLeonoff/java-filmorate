package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendshipService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FriendshipService friendshipService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody NewUserRequest request) throws InternalServerException, ConditionsNotMetException {
        return userService.create(request);
    }

    @PutMapping
    public UserDto update(@RequestBody UpdateUserRequest request) throws InternalServerException {
        return userService.update(request);
    }

    @GetMapping
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }


    @PutMapping("/{id}/friends/{friendId}")
    public List<Friendship.Friend> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("User {} try to become friend with {}", id, friendId);
        return friendshipService.addFriend(id, friendId).getFriends();
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public List<Friendship.Friend> deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        return friendshipService.deleteFriend(id, friendId).getFriends();
    }

    @GetMapping("/{id}/friends")
    public List<Friendship.Friend> getUserFriends(@PathVariable Long id) {
        return friendshipService.getFriends(id).getFriends();
    }

    


}
