package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController controller;

    @BeforeEach
    void setUp() {
        controller = new UserController();
    }

    @Test
    void shouldCreateValidUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        User created = controller.create(user);

        assertNotNull(created.getId());
        assertEquals("test@example.com", created.getEmail());
        assertEquals(1, controller.getAll().size());
    }

    @Test
    void shouldSetNameToLoginIfNameIsBlank() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("login123");
        user.setName(" ");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        User created = controller.create(user);

        assertEquals("login123", created.getName());
    }

    @Test
    void shouldThrowIfEmailInvalid() {
        User user = new User();
        user.setEmail("invalid-email");
        user.setLogin("user");
        user.setName("User");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(ValidationException.class, () -> controller.create(user));
    }

    @Test
    void shouldThrowIfBirthdayInFuture() {
        User user = new User();
        user.setEmail("future@example.com");
        user.setLogin("future");
        user.setName("Future");
        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ValidationException.class, () -> controller.create(user));
    }

    @Test
    void shouldUpdateExistingUser() {
        User user = new User();
        user.setEmail("update@example.com");
        user.setLogin("update");
        user.setName("Update");
        user.setBirthday(LocalDate.of(1995, 5, 5));
        User created = controller.create(user);

        created.setName("Updated Name");
        User updated = controller.update(created);

        assertEquals("Updated Name", updated.getName());
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = new User();
        user1.setEmail("a@a.com");
        user1.setLogin("a");
        user1.setName("A");
        user1.setBirthday(LocalDate.of(1990, 1, 1));

        User user2 = new User();
        user2.setEmail("b@b.com");
        user2.setLogin("b");
        user2.setName("B");
        user2.setBirthday(LocalDate.of(1992, 2, 2));

        controller.create(user1);
        controller.create(user2);

        Collection<User> users = controller.getAll();
        assertEquals(2, users.size());
    }
}