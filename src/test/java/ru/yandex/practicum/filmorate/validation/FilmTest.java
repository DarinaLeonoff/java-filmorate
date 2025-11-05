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
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
public class FilmTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private Film filmGenerator() {
        Film film = new Film();
        film.setName("Title");
        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        film.setReleaseDate(LocalDate.of(1999, 2, 28));
        film.setDuration(200);
        return film;
    }

    @Test
    void shouldBeOk() {
        Film film = filmGenerator();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validationName(String name) {
        Film film = filmGenerator();
        film.setName(name);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi. Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi."})
    public void validationDescription(String desc) {
        Film film = filmGenerator();
        film.setDescription(desc);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void exceptionIfNotValidReleaseDate() {
        Film film = filmGenerator();
        film.setReleaseDate(LocalDate.of(1866, 2, 28));

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {-200, -1})
    public void exceptionIdNegativeDuration(int dur) {
        Film film = filmGenerator();
        film.setDuration(dur);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
    }

}

//Film film = new Film();
//        film.setName("Title");
//        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
//        film.setReleaseDate(LocalDate.of(1999, 2, 28));
//        film.setDuration(200);

//    ConstraintViolationImpl{interpolatedMessage='Название фильма не может быть пустым.', propertyPath=name, rootBeanClass=class ru.yandex.practicum.filmorate.model.Film, messageTemplate='Название фильма не может быть пустым.'}
