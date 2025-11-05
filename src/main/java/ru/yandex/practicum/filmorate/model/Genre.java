package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class Genre {
    private Long id;
    private String name;
}
