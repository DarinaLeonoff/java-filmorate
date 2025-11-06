package ru.yandex.practicum.filmorate.exception;

public class DuplicatedDataException extends Throwable {
    public DuplicatedDataException(String msg) {
        super(msg);
    }
}
