package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private int filmId = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    private int setFilmId() {
        return filmId++;
    }

    @Autowired
    private InMemoryUserStorage userStorage;

    private final Comparator<Film> filmComparator = Comparator.comparing(Film::getLikesCount, Comparator.reverseOrder());

    @Override
    public Film save(Film film) {
        int id = setFilmId();
        film.setId(id);
        films.put(id, film);
        log.info("Добавлен фильм с Id: " + film.getId() + " и названием: " + film.getName());
        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.get(film.getId()) != null) {
            films.put(film.getId(), film);
            log.info("Обновлен фильм с Id: " + film.getId() + " и названием: " + film.getName());
            return film;
        } else {
            throw new FilmNotFoundException("Не найден фильм с id: " + film.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        if (films.get(id) != null) {
            films.remove(id);
        } else {
            throw new FilmNotFoundException("Не найден фильм с id: " + id);
        }
    }

    @Override
    public List<Film> getAll() {
        return List.copyOf(films.values());
    }

    @Override
    public Film get(int id) {
        if (films.get(id) != null) {
            return films.get(id);
        } else {
            throw new FilmNotFoundException("Не найден фильм с id: " + id);
        }
    }

    @Override
    public void putLike(int filmId, int userId) {
        if (userStorage.get(userId) != null) {
            if (films.get(filmId) != null) {
                films.get(filmId).addLike(userId);
            } else {
                throw new FilmNotFoundException("Не найден фильм с id: " + filmId);
            }
        } else {
            throw new UserNotFoundException("Не найден пользователь с id: " + userId);
        }
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        if (userStorage.get(userId) != null) {
            if (films.get(filmId) != null) {
                films.get(filmId).deleteLike(userId);
            } else {
                throw new FilmNotFoundException("Не найден фильм с id: " + filmId);
            }
        } else {
            throw new UserNotFoundException("Не найден пользователь с id: " + userId);
        }
    }

    @Override
    public List<Film> getFilmsByCount(int count) {
        return films
                .values()
                .stream()
                .sorted(filmComparator)
                .limit(count)
                .collect(Collectors.toList());
    }
}
