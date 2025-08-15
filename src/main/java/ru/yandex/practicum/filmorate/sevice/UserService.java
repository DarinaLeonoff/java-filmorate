package ru.yandex.practicum.filmorate.sevice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Slf4j
@Service
public class UserService {
    public User addFriend(User user1, User user2){
       if(!user1.setFriend(user2.getId()) || !user2.setFriend(user1.getId())){
           log.warn("Try to add friends twice.");
           throw new AlreadyExistsException("Пользователи уже являются друзьями");
       }
       user1.setFriend(user2.getId());
       user2.setFriend(user1.getId());
       log.info("Users become friends.");
       return user1;
    }
//    public void deleteFriend(User user){}
//    public Collection<User> mutualFriends(User user){}
}
