package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Primary
public class FilmDBStorage implements FilmStorage {

    int id = 1;
     private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer setIdReturn() {
        return id++;
    }
    @Override
    public Film save(Film film) {

        film.setId(setIdReturn());

        Integer ratingId = film.getMpa().getId();

        String sql = "INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) VALUES (?,?,?,?,?)";

        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), ratingId);
        log.info("Добавлен фильм с Id: " + film.getId() + " и названием: " + film.getName());
        return film;
    }

    @Override
    public Film update(Film film) {

        try {
            get(film.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Не был найден фильм с Id = " + film.getId());
        }

        film.setId(setIdReturn());

        Integer ratingId = film.getMpa().getId();

        String sql = "UPDATE FILMS SET FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATING_ID = ? WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), ratingId, film.getId());
        log.info("Обновлен фильм с Id: " + film.getId() + " и названием: " + film.getName());
        return film;
    }

    @Override
    public void deleteById(int id) {

        Film film = get(id);
        if (film.getId() != null) {
            String sql = "DELETE FROM FILMS WHERE FILM_ID = ?";
            jdbcTemplate.update(sql, id);
            log.info("Удален фильм с Id: " + film.getId());
        } else {
            throw new UserNotFoundException("Не найден фильм с id: " + id);
        }
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT * FROM FILMS";

        try {
            return new ArrayList<>(jdbcTemplate.query(sql, this::mapRowToFilm));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }

    }

    @Override
    public Film get(int id) {

//        String sql = "SELECT FILM_ID, FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID FROM FILMS WHERE FILM_ID = ?";
        String sql = "SELECT f.FILM_ID, f.FILM_NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.RATING_ID, r.RATING_NAME FROM FILMS f JOIN RATING r \n" +
                "\tON f.RATING_ID  = r.RATING_ID " +
                "WHERE f.FILM_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException("Не найден фильм с id: " + id);
        }
    }

    @Override
    public void putLike(int fId, int uId) {
        String sql =  "INSERT INTO LIKES (FILM_ID, USER_ID) VALUES (?,?)";
        jdbcTemplate.update(sql, fId, uId);
    }

    @Override
    public void deleteLike(int fId, int uId) {
        String sql = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, fId, uId);

    }

    @Override
    public List<Film> getFilmsByCount(int count) {

        String sql;

        if (count == 0) {
            sql = "WITH tbl AS (SELECT FILM_ID AS fids, count(LIKE_ID) AS likes FROM LIKES " +
                    " GROUP BY FILM_ID)" +
                    " SELECT f.* FROM FILMS f JOIN tbl ON f.FILM_ID = tbl.fids ORDER BY tbl.LIKES DESC" +
                    " LIMIT 10";
        } else {
            sql = "WITH tbl AS (SELECT FILM_ID AS fids, count(LIKE_ID) AS likes FROM LIKES " +
                    " GROUP BY FILM_ID)" +
                    " SELECT f.* FROM FILMS f JOIN tbl ON f.FILM_ID = tbl.fids ORDER BY tbl.LIKES DESC" +
                    " LIMIT " + count;
        }
        return new ArrayList<>(jdbcTemplate.query(sql, this::mapRowToFilm));
    }

    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film
                .builder()
                .id(rs.getInt("FILM_ID"))
                .name(rs.getString("FILM_NAME"))
                .description(rs.getString("DESCRIPTION"))
                .releaseDate(rs.getDate("RELEASE_DATE").toLocalDate())
                .duration(rs.getLong("DURATION"))
                .mpa(new Rating(rs.getInt("RATING_ID") , rs.getString("RATING_NAME")))
                .build();
    }


}
