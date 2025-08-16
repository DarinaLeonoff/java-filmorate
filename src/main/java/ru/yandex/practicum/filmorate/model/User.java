package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();

    public boolean setFriend(Long userId){
        return friends.add(userId);
    }
    public boolean deleteFriend(Long userId){
        if(!friends.contains(userId)){
            return false;
        }
        friends.remove(userId);
        return true;
    }
}
