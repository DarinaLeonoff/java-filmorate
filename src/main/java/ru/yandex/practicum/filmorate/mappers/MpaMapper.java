package ru.yandex.practicum.filmorate.mappers;

import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

public class MpaMapper {
    public static Mpa mapToMpa (MpaDto dto){
        Mpa mpa = new Mpa();
        mpa.setId(dto.getId());
        return mpa;
    }

    public static Mpa mpaGenerator (Long id){
        Mpa newMpa = new Mpa();
        newMpa.setId(id);
        return newMpa;
    }
}
