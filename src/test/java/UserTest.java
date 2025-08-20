import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

public class UserTest {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void successValidation() {
        User user = new User();
        user.setEmail("email@yandex.ru");
        user.setLogin("TestLogin");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void failIncorrectEmail() {
        User user = new User();
        user.setEmail("emailyandex.ru");
        user.setLogin("TestLogin");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Некорректный email")));
    }

    @Test
    public void failNullEmail() {
        User user = new User();
        user.setEmail(null);
        user.setLogin("TestLogin");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Адрес электронной почты не должен быть пустым.")));
    }

    @Test
    public void failBlankEmail() {
        User user = new User();
        user.setEmail("");
        user.setLogin("TestLogin");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Адрес электронной почты не должен быть пустым.")));
    }

    @Test
    public void failBlankLogin() {
        User user = new User();
        user.setEmail("email@yandex.ru");
        user.setLogin("");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Логин не должен быть пустым.")));
    }

    @Test
    public void failNullLogin() {
        User user = new User();
        user.setEmail("email@yandex.ru");
        user.setLogin(null);
        user.setName("Name");
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Логин не должен быть пустым.")));
    }

    @Test
    public void failSpaceInLogin() {
        User user = new User();
        user.setEmail("email@yandex.ru");
        user.setLogin("test login");
        user.setName("Name");
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Логин не должен содержать пробелы")));
    }

    @Test
    public void failBlankName() {
        User user = new User();
        user.setEmail("email@yandex.ru");
        user.setLogin("TestLogin");
        user.setName(" ");
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Имя не должно быть пустым.")));
    }

    @Test
    public void failNullName() {
        User user = new User();
        user.setEmail("email@yandex.ru");
        user.setLogin("TestLogin");
        user.setName(null);
        user.setBirthday(LocalDate.of(2001, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Имя не должно быть пустым.")));
    }

    @Test
    public void failFutureBirthday() {
        User user = new User();
        user.setEmail("email@yandex.ru");
        user.setLogin("TestLogin");
        user.setName(null);
        user.setBirthday(LocalDate.of(2222, 2, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("День рождения не может быть в будущем.")));
    }

}

//    User user = new User();
//        user.setEmail("email@yandex.ru");
//                user.setLogin("TestLogin");
//                user.setName("Name");
//                user.setBirthday(LocalDate.of(2001, 2,21));

