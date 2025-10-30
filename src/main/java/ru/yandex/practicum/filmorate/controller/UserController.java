package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody NewUserRequest request) throws InternalServerException, ConditionsNotMetException {
        return userService.create(request);
    } //id: null

//    @PutMapping
//    public User update(@Valid @RequestBody UpdateUserRequest request) throws InternalServerException {
//        return userService.update(request);
//    }

    @GetMapping
    public Collection<User> getAll() {
        return userService.getAll();
    }//correct

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }//correct


//    @PutMapping("/{id}/friends/{friendId}")
//    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
//        return userService.addFriend(id, friendId);
//    }
//
//    @DeleteMapping("/{id}/friends/{friendId}")
//    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
//        return userService.deleteFriend(id, friendId);
//    }
//
//    @GetMapping("/{id}/friends/common/{otherId}")
//    public Collection<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
//        return userService.mutualFriends(id, otherId);
//    }
//
//    @GetMapping("/{id}/friends")
//    public Collection<User> getUserFriends(@PathVariable Long id) {
//        return userService.getFriends(id);
//    }

}
