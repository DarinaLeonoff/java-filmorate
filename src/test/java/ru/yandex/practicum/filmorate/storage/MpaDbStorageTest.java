package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, FilmRowMapper.class, FilmService.class,
        GenreService.class, GenreDbStorage.class,
        LikesDbStorage.class, LikesService.class,
        MpaDbStorage.class, MpaService.class,
        UserService.class, UserDbStorage.class, UserRowMapper.class})
public class MpaDbStorageTest {
    private final MpaDbStorage mpaDbStorage;

    @Test
    public void getAllRatingsTest() {
        int totalCount = 5;
        List<Mpa> mpas = mpaDbStorage.getAllRatings();

        assertEquals(totalCount, mpas.size(), "Have to be " + totalCount);
    }

    @Test
    public void getByIdTest() {
        Long id = 1l;
        String name = "G";
        Mpa mpas = mpaDbStorage.getById(id);

        assertEquals(id, mpas.getId(), "Have to be " + id);
        assertEquals(name, mpas.getName(), "Have to be " + name);
    }
}
