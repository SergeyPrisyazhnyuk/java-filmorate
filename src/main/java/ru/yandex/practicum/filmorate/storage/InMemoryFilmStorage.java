package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
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

    private final InMemoryUserStorage userStorage = new InMemoryUserStorage();

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
    public void deleteById(int Id) {
        if (films.get(Id) != null) {
            films.remove(Id);
        } else {
            throw new FilmNotFoundException("Не найден фильм с id: " + Id);
        }
    }

    @Override
    public List<Film> getAll() {
        return List.copyOf(films.values());
    }

    @Override
    public Film get(int Id) {
        if (films.get(Id) != null) {
            return films.get(Id);
        } else {
            throw new FilmNotFoundException("Не найден фильм с id: " + Id);
        }
    }

    @Override
    public void putLike(int fId, int uId) {
        if (userStorage.get(uId) != null) {
            if (films.get(fId) != null) {
                films.get(fId).addLike(uId);
            } else {
                throw new FilmNotFoundException("Не найден фильм с id: " + fId);
            }
        } else {
            throw new UserNotFoundException("Не найден пользователь с id: " + uId);
        }
    }

    @Override
    public void deleteLike(int fId, int uId) {
        if (userStorage.get(uId) != null) {
            if (films.get(fId) != null) {
                films.get(fId).deleteLike(uId);
            } else {
                throw new FilmNotFoundException("Не найден фильм с id: " + fId);
            }
        } else {
            throw new UserNotFoundException("Не найден пользователь с id: " + uId);
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
