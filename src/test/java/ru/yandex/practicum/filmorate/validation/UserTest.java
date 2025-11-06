package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
public class UserTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void successValidation() {
        User user = userGenerator();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"emailyandex.ru", "email@", "@yandex.ru"})
    public void failIncorrectEmail(String email) {
        User user = userGenerator();
        user.setEmail(email);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"test test", " test"})
    public void failBlankLogin(String login) {
        User user = userGenerator();
        user.setLogin(login);
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void failBlankName(String name) {
        User user = userGenerator();
        user.setName(name);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
    }


    @Test
    public void failFutureBirthday() {
        User user = userGenerator();
        user.setBirthday(LocalDate.of(2222, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("День рождения не может быть в будущем.")));
    }

    private User userGenerator() {
        User user = new User();
        user.setEmail("email@yandex.ru");
        user.setLogin("TestLogin");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2001, 2, 21));
        return user;
    }

}

//    User user = new User();
//        user.setEmail("email@yandex.ru");
//                user.setLogin("TestLogin");
//                user.setName("Name");
//                user.setBirthday(LocalDate.of(2001, 2,21));

