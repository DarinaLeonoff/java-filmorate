package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RatingDbStorage {
    private static final String GET_RATING = "SELECT rating_name FROM films f LEFT JOIN mpa ON  f.rating_id = mpa.rating_id WHERE film_id = :filmId;";
    private static final String GET_RATINGS = "SELECT * FROM mpa;";
    private static final String GET_RATING_BY_ID = "SELECT * FROM mpa WHERE rating_id = :ratingId;";
    private final NamedParameterJdbcTemplate jdbc;

    public String getFilmRating(Long filmId){
        Map<String,Long> film = Collections.singletonMap("filmId", filmId);
        return jdbc.queryForObject(GET_RATING, film, String.class);
    }

    public List<MpaDto> getAllRatings(){
        return jdbc.query(GET_RATINGS,
                (rs, rowNum) -> {
            MpaDto mpa = new MpaDto();
            mpa.setId(rs.getLong("rating_id"));
            mpa.setName(rs.getString("rating_name"));
            return mpa;
                });
    }

    public MpaDto getById(Long id){
        Map<String,Long> mpaId = Collections.singletonMap("ratingId", id);
        return jdbc.queryForObject(GET_RATING_BY_ID, mpaId,
                (rs, rowNum)->{
            MpaDto dto = new MpaDto();
            dto.setId(rs.getLong("rating_id"));
            dto.setName(rs.getString("rating_name"));
            return dto;
                });
    }

}
