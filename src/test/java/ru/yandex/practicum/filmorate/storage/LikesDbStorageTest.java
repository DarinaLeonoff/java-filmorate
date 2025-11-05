package ru.yandex.practicum.filmorate.storage;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, FilmRowMapper.class, FilmService.class,
        GenreService.class, GenreDbStorage.class,
        LikesDbStorage.class, LikesService.class,
        MpaDbStorage.class, MpaService.class,
        UserService.class, UserDbStorage.class, UserRowMapper.class})
public class LikesDbStorageTest {
    public final LikesDbStorage likesDbStorage;
    public final FilmService filmService;
    public final UserService userService;

    private NewFilmRequest generateNewFilmRequest() {
        NewFilmRequest request = new NewFilmRequest();
        request.setName("Интерстеллар");
        request.setDescription("Захватывающий научно-фантастический фильм");
        request.setDuration(169);
        request.setReleaseDate(LocalDate.of(2014, 11, 7));
        Mpa mpa = new Mpa();
        mpa.setId(1l);
        request.setMpa(mpa);
        request.setGenres(new ArrayList<>());

        return request;
    }

    private NewUserRequest generateNewUserRequest(){
        NewUserRequest request = new NewUserRequest();
        request.setName("name" + Math.random());
        request.setEmail("name@gmail.com");
        request.setBirthday(LocalDate.now());
        request.setLogin("name123");
        return request;
    }

    @Test
    public void setAndGetLikeTest() throws InternalServerException, ConditionsNotMetException {
        FilmDto film = filmService.addFilm(generateNewFilmRequest());
        UserDto user = userService.create(generateNewUserRequest());

        likesDbStorage.setLike(film.getId(), user.getId());

        assertEquals(1, likesDbStorage.getLikes(film.getId()).size(), "Have to be 1");
    }


    @Test
    public void deleteAndCountLikeTest() throws InternalServerException, ConditionsNotMetException {
        FilmDto film = filmService.addFilm(generateNewFilmRequest());
        UserDto user = userService.create(generateNewUserRequest());
        UserDto user2 = userService.create(generateNewUserRequest());
        likesDbStorage.setLike(film.getId(), user.getId());
        likesDbStorage.setLike(film.getId(), user2.getId());

        likesDbStorage.deleteLike(film.getId(), user.getId());
        List<Long> likes = new ArrayList<>(likesDbStorage.getLikes(film.getId()));
        int likecCount = likesDbStorage.getLikesCount(film.getId());

        assertEquals(likecCount, likes.size(), "Have to be the same");
        assertEquals(user2.getId(), likes.get(0));

    }

    @Test
    public void GetAndCountLikesForListTest() throws InternalServerException, ConditionsNotMetException {
        FilmDto film = filmService.addFilm(generateNewFilmRequest());
        FilmDto film2 = filmService.addFilm(generateNewFilmRequest());
        FilmDto film3 = filmService.addFilm(generateNewFilmRequest());
        FilmDto film4 = filmService.addFilm(generateNewFilmRequest());
        List<Long> films = List.of(film.getId(), film2.getId(), film3.getId(), film4.getId());

        UserDto user = userService.create(generateNewUserRequest());
        UserDto user2 = userService.create(generateNewUserRequest());
        likesDbStorage.setLike(film.getId(), user.getId());
        likesDbStorage.setLike(film.getId(), user2.getId());
        likesDbStorage.setLike(film2.getId(), user.getId());
        likesDbStorage.setLike(film3.getId(), user2.getId());
        likesDbStorage.setLike(film4.getId(), user.getId());

        Map<Long, Set<Long>> map = likesDbStorage.getLikesForList(films);
        Map<Long, Integer> countMap = likesDbStorage.getLikesCountForList(films);

        assertEquals(map.size(), countMap.size(), "Have to be the same");
        for(Long id : map.keySet()){
            assertEquals(map.get(id).size(), countMap.get(id), "Have to be the same");
        }
    }

}
