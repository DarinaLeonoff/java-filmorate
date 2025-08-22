package ru.yandex.practicum.filmorate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ErrorResponse {
    @Getter
    private String error, description;
}
