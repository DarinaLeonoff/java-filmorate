package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.service.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, FilmRowMapper.class, FilmService.class,
        GenreService.class, GenreDbStorage.class,
        LikesDbStorage.class, LikesService.class,
        MpaDbStorage.class, MpaService.class,
        UserService.class, UserDbStorage.class, UserRowMapper.class,
        FriendshipDbStorage.class, FriendshipService.class})
public class FriendshipDnStorageTest {
    private final FriendshipDbStorage friendshipDbStorage;
    private final UserService userService;

    private NewUserRequest generateNewUserRequest(){
        NewUserRequest request = new NewUserRequest();
        request.setName("name" + Math.random());
        request.setEmail("name@gmail.com");
        request.setBirthday(LocalDate.now());
        request.setLogin("name123");
        return request;
    }
    private List<Long> generateUsers(int count) throws ConditionsNotMetException, InternalServerException {
        List<Long> ids = new ArrayList<>();
        while(count > 0){
           ids.add(userService.create(generateNewUserRequest()).getId());
            count --;
        }
        return ids;
    }

    @Test
    public void TestCreateAndGetFriends() throws ConditionsNotMetException, InternalServerException {
        List<Long> ids = generateUsers(4);
        friendshipDbStorage.addFriend(ids.get(0), ids.get(1));
        friendshipDbStorage.addFriend(ids.get(0), ids.get(2));
        friendshipDbStorage.addFriend(ids.get(0), ids.get(3));

        List<Long> friends = friendshipDbStorage.getFriendsList(ids.get(0));
        assertEquals(3, friends.size(), "Have to be 3");
    }

    @Test
    public void TestDeleteFriends() throws ConditionsNotMetException, InternalServerException {
        List<Long> ids = generateUsers(4);
        friendshipDbStorage.addFriend(ids.get(0), ids.get(1));
        friendshipDbStorage.addFriend(ids.get(0), ids.get(2));
        friendshipDbStorage.addFriend(ids.get(0), ids.get(3));

        List<Long> friends = friendshipDbStorage.getFriendsList(ids.get(0));
        List<Long> friendsLeft = friendshipDbStorage.deleteFriend(ids.get(0), (ids.get(3)));
        assertEquals(friends.size()-1, friendsLeft.size(), "Have to be 3");
    }

    @Test
    public void TestGetCommonFriends() throws ConditionsNotMetException, InternalServerException {
        List<Long> ids = generateUsers(4);
        friendshipDbStorage.addFriend(ids.get(0), ids.get(1));
        friendshipDbStorage.addFriend(ids.get(1), ids.get(2));
        friendshipDbStorage.addFriend(ids.get(1), ids.get(3));
        List<Long> friends = friendshipDbStorage.getCommonFriendsList(ids.get(0), ids.get(3));
        assertEquals(1, friends.size(), "Have to be 1");
        assertEquals(ids.get(1), friends.get(0), "Have to be "+ ids.get(1));
    }

}
