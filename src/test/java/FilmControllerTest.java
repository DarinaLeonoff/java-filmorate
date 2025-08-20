import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

//Spring boot don't throw exception in tests
//Film validation in FilmTest
class FilmControllerTest {
    private FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new UserService(new InMemoryUserStorage())));
    private static Film validFilm = new Film();
    private static Film invalidFilm = new Film();

    @BeforeAll
    public static void setup() {
        validFilm.setName("Title");
        validFilm.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        validFilm.setReleaseDate(LocalDate.of(1999, 2, 28));
        validFilm.setDuration(200);

        invalidFilm.setName("");
        invalidFilm.setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit quisque faucibus ex sapien vitae pellentesque sem placerat in id cursus mi.");
        invalidFilm.setReleaseDate(LocalDate.of(1999, 2, 28));
        invalidFilm.setDuration(200);
    }

    @Test
    void shouldAddValidFilm() {
        Film added = filmController.add(validFilm);

        assertNotNull(added.getId());
        assertEquals("Title", added.getName());

        Collection<Film> allFilms = filmController.getAll();
        assertEquals(1, allFilms.size());
    }

    @Test
    void shouldFailValidationForEmptyName() {
        Film added = filmController.add(invalidFilm);

        assertThrows(MethodArgumentNotValidException.class, () -> filmController.add(validFilm));
    }

    @Test
    void shouldFailValidationForLongDescription() {
        validFilm.setDescription("A".repeat(201));

        assertThrows(MethodArgumentNotValidException.class, () -> filmController.add(validFilm));
    }

    @Test
    void shouldFailValidationForOldReleaseDate() {
        validFilm.setReleaseDate(LocalDate.of(1800, 1, 1));

        assertThrows(ValidationException.class, () -> filmController.add(validFilm));
    }

    @Test
    void shouldFailValidationForNegativeDuration() {
        validFilm.setDuration(-10);

        assertThrows(ValidationException.class, () -> filmController.add(validFilm));
    }

    @Test
    void shouldReturnAllFilms() {
        filmController.add(validFilm);
        Collection<Film> allFilms = filmController.getAll();

        assertEquals(1, allFilms.size());
        assertEquals("Inception", allFilms.iterator().next().getName());
    }

    @Test
    void shouldUpdateFilm() {
        Film added = filmController.add(validFilm);

        added.setDescription("Updated description");
        Film updated = filmController.update(added);

        assertEquals("Updated description", updated.getDescription());
        assertEquals(1, filmController.getAll().size());
    }

    @Test
    void shouldThrowWhenUpdatingNonexistentFilm() {
        validFilm.setId(99L);

        assertThrows(RuntimeException.class, () -> filmController.update(validFilm));
    }
}