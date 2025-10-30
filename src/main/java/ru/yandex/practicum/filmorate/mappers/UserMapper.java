package ru.yandex.practicum.filmorate.mappers;

import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

public class UserMapper {
    public static UserDto mapToDto(User user){
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBirthday(user.getBirthday());
        dto.setLogin(user.getLogin());

        return dto;
    }

    public static User mapToUser(NewUserRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setBirthday(request.getBirthday());
        user.setLogin(request.getLogin());

        return user;
    }

    public static User updateUser(UpdateUserRequest request){
        User user = new User();
        if(request.hasName()){
            user.setName(request.getName());
        }
        if(request.hasEmail()){
            user.setEmail(request.getEmail());
        }
        if(request.hasBirthday()){
            user.setBirthday(request.getBirthday());
        }
        if(request.hasLogin()){
            user.setLogin(request.getLogin());
        }

        return user;
    }
}
