package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {


    private final FilmStorage filmStorage;

    public Film saveFilm(Film film) {
        return filmStorage.save(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> getFilms() {
        return filmStorage.getAll();
    }

    public Film getById(int id) {
        return filmStorage.get(id);
    }

    public void deleteById(int id) {
        filmStorage.deleteById(id);
    }

    public void putLike(int filmId, int userId) {
        filmStorage.putLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        filmStorage.deleteLike(filmId, userId);
    }

    public List<Film> getFilmsByCount(int count) {
        return filmStorage.getFilmsByCount(count);
    }

}
