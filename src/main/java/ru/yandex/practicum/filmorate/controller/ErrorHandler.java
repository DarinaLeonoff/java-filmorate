package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.AlreadyExistsException;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(final MethodArgumentNotValidException e) {
        return new ErrorResponse("Not valid", e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExists(final AlreadyExistsException e) {
        return new ErrorResponse("Resource already exist", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoCandidates(final NoCandidatesFoundException e) {
        return new ErrorResponse("Candidates not found", e.getMessage());
    }

}
