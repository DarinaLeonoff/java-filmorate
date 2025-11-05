package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, FilmRowMapper.class, FilmService.class,
        GenreService.class, GenreDbStorage.class,
        LikesDbStorage.class, LikesService.class,
        MpaDbStorage.class, MpaService.class,
        UserService.class, UserDbStorage.class, UserRowMapper.class})
public class UserDbStorageTest {
    private final UserDbStorage userDbStorage;

    private User generateUser() {
        User user = new User();
        user.setName("name" + Math.random());
        user.setEmail("name@gmail.com");
        user.setBirthday(LocalDate.now());
        user.setLogin("name123");
        return user;
    }

    @Test
    public void createAndGetTest() throws InternalServerException {
        User user = userDbStorage.create(generateUser());
        User getUser = userDbStorage.getUser(user.getId());

        assertEquals(user.getId(), getUser.getId(), "Have to be the same");
        assertEquals(user.getName(), getUser.getName(), "Have to be the same");
    }

    @Test
    public void updateUserTest() throws InternalServerException {
        User user = userDbStorage.create(generateUser());
        User user2 = generateUser();
        user2.setId(user.getId());

        User userUpdated = userDbStorage.update(user2);

        assertEquals(user.getId(), userUpdated.getId(), "Have to be the same");
        assertNotEquals(user.getName(), userUpdated.getName(), "Have to be different");
        assertEquals(user2.getName(), userUpdated.getName(), "Have to be same");
    }

    @Test
    public void getAllAndDeleteTest() throws InternalServerException {
        User user1 = userDbStorage.create(generateUser());
        User user2 = userDbStorage.create(generateUser());
        User user3 = userDbStorage.create(generateUser());
        User user4 = userDbStorage.create(generateUser());

        List<User> users1 = userDbStorage.getAll();
        userDbStorage.deleteUser(user4);
        List<User> users2 = userDbStorage.getAll();

        assertEquals(users1.size()-1, users2.size(), "Have to be the same");
    }
}
