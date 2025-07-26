package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private Map<Long, User> users = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public User create(@RequestBody User user){
        userValidation(user);
        log.info("Start to create user {}", user.getName());
        user.setId(newId());
        users.put(user.getId(), user);
        log.debug("User was added with id = {}", user.getId());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user){
        if(!users.containsKey(user.getId())){
            log.warn("No user with id = {}", user.getId());
            throw new NoCandidatesFoundException("Can not find user by id = " + user.getId());
        }
        userValidation(user);
        users.put(user.getId(), user);
        log.debug("User with id = {} was updated", user.getId());
        return user;
    }

    @GetMapping
    public Collection<User> getAll(){
        return users.values();
    }

    private Long newId(){
        return users.keySet().stream().mapToLong(id -> id).max().orElse(0)+1;
    }

    private void userValidation(User user){
        String email = user.getEmail();
        if(email==null || email.isBlank() || !email.contains("@")){
            log.warn("Try to add invalid email");
            throw new ValidationException("Адрес электронной почти не должен быть пустым и должен содержать @.");
        }

        String login = user.getLogin();
        if(login ==null || login.isBlank() ||login.contains(" ")){
            log.warn("Try to add invalid login");
            throw new ValidationException("Логин не должен быть пустым и содержать пробелы.");
        }

        String name = user.getName();
        if(name == null || name.isBlank()){
            log.warn("Empty name is replaced with login");
            user.setName(login);
        }

        LocalDate birthday = user.getBirthday();
        if(birthday.isAfter(LocalDate.now())){
            log.warn("Try to ad birthday in future");
            throw new ValidationException("День рождения не может быть в будущем.");
        }
    }
}
