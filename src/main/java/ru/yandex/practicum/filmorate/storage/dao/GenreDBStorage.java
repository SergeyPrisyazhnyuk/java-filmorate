package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenreDBStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenres() {

        List<Genre> genres = new ArrayList<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT GENRE_ID , GENRE_NAME FROM GENRES");

        while ((genreRows.next())) {
            Genre genre = Genre
                    .builder()
                    .genreId(genreRows.getInt("GENRE_ID"))
                    .genreName(genreRows.getString("GENRE_NAME"))
                    .build();
            genres.add(genre);
        }

        return genres;
    }

    @Override
    public Genre getGenreById(Integer id) {

        String sql = "SELECT GENRE_ID , GENRE_NAME FROM GENRES WHERE GENRE_ID = ?";

        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToGenre, id);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Жанр с id = " + id + " не найден в справочнике жанров");
        }
    }

    private Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre
                .builder()
                .genreId(rs.getInt("GENRE_ID"))
                .genreName(rs.getString("GENRE_NAME"))
                .build();

    }


}
