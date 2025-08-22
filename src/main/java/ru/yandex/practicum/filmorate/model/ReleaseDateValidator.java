package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateNotBefore, LocalDate> {

    private LocalDate minDate;

    @Override
    public void initialize(ReleaseDateNotBefore constraintAnnotation) {
        minDate = LocalDate.parse(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; // @NotNull уже проверяет отдельно
        }
        return !date.isBefore(minDate);
    }
}

