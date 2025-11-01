package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.FriendshipDbStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipDbStorage friendshipDbStorage;
    public Friendship getFriends(Long id){
        return friendshipDbStorage.getFriendsList(id);
    }
}
