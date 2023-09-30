package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RatingDBStorage implements RatingStorage {

    private final JdbcTemplate jdbcTemplate;


    public RatingDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Rating getRatingById(Integer id) {

        String sql = "SELECT RATING_ID, RATING_NAME FROM RATING WHERE RATING_ID = ?";

        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToRating, id);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Рейтинг с id = " + id + " не найден в справочнике рейтингов");
        }
    }

    @Override
    public void addRatingToFilm(Film film) {

    }

    @Override
    public List<Rating> getAllRatings() {

        List<Rating> ratings = new ArrayList<>();
        SqlRowSet ratingRows = jdbcTemplate.queryForRowSet("SELECT RATING_ID, RATING_NAME FROM RATING");

        while ((ratingRows.next())) {
            Rating rating = Rating
                    .builder()
                    .id(ratingRows.getInt("RATING_ID"))
                    .ratingName(ratingRows.getString("RATING_NAME"))
                    .build();
            ratings.add(rating);
        }

        return ratings;

    }

    private Rating mapRowToRating(ResultSet rs, int rowNum) throws SQLException {
        return Rating
                .builder()
                .id(rs.getInt("RATING_ID"))
                .ratingName(rs.getString("RATING_NAME"))
                .build();

    }


}
