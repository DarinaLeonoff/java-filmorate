package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friendship {
    private final User user;
    private final User friend;
    private Boolean status = false;

    public void addFriend(boolean answer) {
        status = answer;
    }
}
