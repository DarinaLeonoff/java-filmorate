package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController filmController;
    private Film validFilm;

//    @BeforeEach
//    void setUp() {
//        filmController = new FilmController();
//
//        validFilm = new Film();
//        validFilm.setName("Inception");
//        validFilm.setDescription("A mind-bending thriller.");
//        validFilm.setReleaseDate(LocalDate.of(2010, 7, 16));
//        validFilm.setDuration(148);
//    }
//
//    @Test
//    void shouldAddValidFilm() {
//        Film added = filmController.add(validFilm);
//
//        assertNotNull(added.getId());
//        assertEquals("Inception", added.getName());
//
//        Collection<Film> allFilms = filmController.getAll();
//        assertEquals(1, allFilms.size());
//    }
//
//    @Test
//    void shouldFailValidationForEmptyName() {
//        validFilm.setName("");
//
//        assertThrows(ValidationException.class, () -> filmController.add(validFilm));
//    }
//
//    @Test
//    void shouldFailValidationForLongDescription() {
//        validFilm.setDescription("A".repeat(201));
//
//        assertThrows(ValidationException.class, () -> filmController.add(validFilm));
//    }
//
//    @Test
//    void shouldFailValidationForOldReleaseDate() {
//        validFilm.setReleaseDate(LocalDate.of(1800, 1, 1));
//
//        assertThrows(ValidationException.class, () -> filmController.add(validFilm));
//    }
//
//    @Test
//    void shouldFailValidationForNegativeDuration() {
//        validFilm.setDuration(-10);
//
//        assertThrows(ValidationException.class, () -> filmController.add(validFilm));
//    }
//
//    @Test
//    void shouldReturnAllFilms() {
//        filmController.add(validFilm);
//        Collection<Film> allFilms = filmController.getAll();
//
//        assertEquals(1, allFilms.size());
//        assertEquals("Inception", allFilms.iterator().next().getName());
//    }
//
//    @Test
//    void shouldUpdateFilm() {
//        Film added = filmController.add(validFilm);
//
//        added.setDescription("Updated description");
//        Film updated = filmController.update(added);
//
//        assertEquals("Updated description", updated.getDescription());
//        assertEquals(1, filmController.getAll().size());
//    }
//
//    @Test
//    void shouldThrowWhenUpdatingNonexistentFilm() {
//        validFilm.setId(99L);
//
//        assertThrows(RuntimeException.class, () -> filmController.update(validFilm));
//    }
}
