import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

public class FilmTest {
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldBeOk(){
        Film film = new Film();
        film.setName("Title");
        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        film.setReleaseDate(LocalDate.of(1999, 2, 28));
        film.setDuration(200);
        Set<ConstraintViolation<Film>> violations  = validator.validate(film);
        Assertions.assertTrue(violations.isEmpty());
    }
    @Test
    void exceptionIfNoName(){
        Film film = new Film();
        film.setName("");
        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        film.setReleaseDate(LocalDate.of(1999, 2, 28));
        film.setDuration(200);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("Название фильма не может быть пустым."))
        );
    }
    @Test
    void exceptionIfNameNull(){
        Film film = new Film();
        film.setName(null);
        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        film.setReleaseDate(LocalDate.of(1999, 2, 28));
        film.setDuration(200);

        Set<ConstraintViolation<Film>> violations  = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("Название фильма не может быть пустым."))
        );
    }
    @Test
    void exceptionIfLongDescription(){
        Film film = new Film();
        film.setName("Title");
        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae " +
                "pellentesque sem placerat in id cursus mi. Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        film.setReleaseDate(LocalDate.of(1999, 2, 28));
        film.setDuration(200);

        Set<ConstraintViolation<Film>> violations  = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("Описание фильма не может превышать 200 символов."))
        );
    }

    @Test
    void exceptionIfNullDescription(){
        Film film = new Film();
        film.setName("Title");
        film.setDescription(null);
        film.setReleaseDate(LocalDate.of(1999, 2, 28));
        film.setDuration(200);
        Set<ConstraintViolation<Film>> violations  = validator.validate(film);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void exceptionIfNotValidReleaseDate(){
        Film film = new Film();
        film.setName("Title");
        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        film.setReleaseDate(LocalDate.of(1866, 2, 28));
        film.setDuration(200);

        Set<ConstraintViolation<Film>> violations  = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("Фильм не может быть выпущен до 1895-12-28."))
        );
    }
    @Test
    void exceptionIfNullReleaseDate(){
        Film film = new Film();
        film.setName("Title");
        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        film.setReleaseDate(null);
        film.setDuration(200);

        Set<ConstraintViolation<Film>> violations  = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("Не указана дата выхода фильма."))
        );
    }

    @Test
    void exceptionIdNegativeDuration(){
        Film film = new Film();
        film.setName("Title");
        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        film.setReleaseDate(LocalDate.of(1999, 2, 28));
        film.setDuration(-100);

        Set<ConstraintViolation<Film>> violations  = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("Фильм не может длиться отрицательное количество времени."))
        );
    }

}

//Film film = new Film();
//        film.setName("Title");
//        film.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
//        film.setReleaseDate(LocalDate.of(1999, 2, 28));
//        film.setDuration(200);

//    ConstraintViolationImpl{interpolatedMessage='Название фильма не может быть пустым.', propertyPath=name, rootBeanClass=class ru.yandex.practicum.filmorate.model.Film, messageTemplate='Название фильма не может быть пустым.'}
