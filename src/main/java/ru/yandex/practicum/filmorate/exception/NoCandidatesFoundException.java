package ru.yandex.practicum.filmorate.exception;

public class NoCandidatesFoundException extends RuntimeException {
    public NoCandidatesFoundException(String message) {
        super(message);
    }
}
