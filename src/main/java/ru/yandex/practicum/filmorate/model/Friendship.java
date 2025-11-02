package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class Friendship {
    private Long id;
    private List<Friend> friends = new ArrayList<>();

    public void  setFriend(Long id){
        friends.add(new Friend(id));
    }
    @AllArgsConstructor
    @Data
    public static class Friend{
        private Long id;
    }
}
