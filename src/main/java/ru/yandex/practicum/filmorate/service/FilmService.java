package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class FilmService {

    private int filmId = 1;

    private final Map<Integer, Film> films = new HashMap<>();

    private int setFilmId() {
        return filmId++;
    }

    public Film saveFilm(Film film) {
        int id = setFilmId();
        film.setId(id);
        films.put(id, film);
        log.info("Добавлен фильм с Id: " + film.getId() + " и названием: " + film.getName());
        return film;
    }

    public Film updateFilm(Film film) {
        if (films.get(film.getId()) != null) {
            films.put(film.getId(), film);
            log.info("Обновлен фильм с Id: " + film.getId() + " и названием: " + film.getName());
            return film;
        } else {
            throw new FilmNotFoundException("Не найден фильм с id: " + film.getId());
        }
    }

    public List<Film> getFilms() {
        return List.copyOf(films.values());
    }

}
