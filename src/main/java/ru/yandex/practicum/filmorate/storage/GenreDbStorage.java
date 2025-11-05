package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.exception.NoCandidatesFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GenreDbStorage {
    private static final String GET_GENRES = "SELECT * FROM film_genre f LEFT JOIN genre g ON f.genre_id = g.genre_id WHERE film_id = (:filmId) ORDER BY genre_id ASC ;";
    private static final String GET_ALL_GENRES = "SELECT * FROM genre ORDER BY genre_id ASC;";
    private static final String GET_GENRE = "SELECT genre_name FROM genre WHERE genre_id = (:genreId);";
    private static final String GET_GENRES_FOR_LIST = "SELECT * FROM film_genre f LEFT JOIN genre g ON " +
            "f.genre_id = g.genre_id WHERE f.film_id IN (:films);";
    private static final String SET_GENRES = "INSERT INTO film_genre(film_id, genre_id) VALUES(:filmId, :genreId);";
    private final NamedParameterJdbcTemplate jdbc;

    public List<GenreDto> getGenres(Long filmId) {
        Map<String, Long> film = Collections.singletonMap("filmId", filmId);

        List<GenreDto> genres = jdbc.query(GET_GENRES, film,
                (rs, rowNum) -> {
                    GenreDto genre = new GenreDto();
                    genre.setId(rs.getLong("genre_id"));
                    genre.setName(rs.getString("genre_name"));
                    return genre;
                });
        return genres;
    }

    public List<GenreDto> getAllGenres() {
        return jdbc.query(GET_ALL_GENRES,
                (rs, rowNum) -> {
                    GenreDto genre = new GenreDto();
                    genre.setId(rs.getLong("genre_id"));
                    genre.setName(rs.getString("genre_name"));
                    return genre;
                });
    }

    public Map<Long, List<GenreDto>> getGenresForList(List<Long> films){
        Map<Long, List<GenreDto>> result = new HashMap<>();
        Map<String, List<Long>> param = Collections.singletonMap("films", films);
        jdbc.query(GET_GENRES_FOR_LIST, param, (rs, rowNum) -> {
            Long id = rs.getLong("film_id");
            GenreDto dto = new GenreDto();
            dto.setId(rs.getLong("genre_id"));
            dto.setName(rs.getString("genre_name"));
            List<GenreDto> dtoList = result.get(id);
            if(dtoList == null){
                dtoList = new ArrayList<>();
            }
            dtoList.add(dto);
            result.put(id, dtoList);
            return dto;
        } );
        return result;
    }

    public GenreDto getGenre(Long id) {
        isIdValid(id);
        Map<String, Long> genreId = Collections.singletonMap("genreId", id);
        GenreDto genre = jdbc.queryForObject(GET_GENRE, genreId, (rs, rowNum) -> {
            GenreDto dto = new GenreDto();
            dto.setId(id);
            dto.setName(rs.getString("genre_name"));
            return dto;
        });

        return genre;
    }

    public void setGenres(Long filmId, List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            log.info("Film without genres.");
            return;
        }
        Set<Genre> genreSet = new HashSet<>(genres);
        for (Genre genre : genreSet) {
            Long genreId = genre.getId();
            isIdValid(genreId);
            jdbc.batchUpdate(SET_GENRES, new SqlParameterSource[]{
                    new MapSqlParameterSource("filmId", filmId).addValue("genreId", genreId)
            });
        }
    }

    private void isIdValid(Long id) {
        List<Long> genres = jdbc.query(GET_ALL_GENRES,
                (rs, rowNum) -> rs.getLong("genre_id"));
        if (!genres.contains(id)) {
            throw new NoCandidatesFoundException("Жанр не найден");
        }
    }

}
