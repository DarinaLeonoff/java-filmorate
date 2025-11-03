package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MpaDbStorage {
    private static final String GET_RATING = "SELECT * FROM films f LEFT JOIN mpa ON  f.rating_id = mpa.rating_id " +
            "WHERE film_id = :filmId;";
    private static final String GET_RATINGS = "SELECT * FROM mpa;";
    private static final String GET_RATING_BY_ID = "SELECT * FROM mpa WHERE rating_id = :ratingId;";
    private final NamedParameterJdbcTemplate jdbc;

    public MpaDto getFilmRating(Long filmId){
        Map<String,Long> film = Collections.singletonMap("filmId", filmId);
        MpaDto dto = jdbc.queryForObject(GET_RATING, film,
                (rs, rowNum) -> {
            MpaDto mpaDto = new MpaDto();
            Long id = rs.getLong("rating_id");
            isIdValid(id);
            mpaDto.setId(id);
            mpaDto.setName(rs.getString("rating_name"));
            return mpaDto;
                });
        return dto;
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

   public boolean isIdValid(Long id){
        List<Long> ratings = jdbc.query(GET_RATINGS,
                (rs, rowNum) -> rs.getLong("rating_id"));
        if(!ratings.contains(id)){
            log.warn("mpa {} is not valid", id);
            throw new NoCandidatesFoundException("Mpa не найден");
        }
        return true;
    }
}
