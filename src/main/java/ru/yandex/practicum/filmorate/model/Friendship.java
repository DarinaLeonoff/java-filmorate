package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.List;

@Data
public class Friendship {
    private final Long userId;
    private final List<Long> friendId;

}
