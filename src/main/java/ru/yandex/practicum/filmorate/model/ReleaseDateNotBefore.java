package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReleaseDateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReleaseDateNotBefore {
    String value(); // Минимальная дата в формате ISO (yyyy-MM-dd)

    String message() default "Фильм не может быть выпущен до {value}.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

