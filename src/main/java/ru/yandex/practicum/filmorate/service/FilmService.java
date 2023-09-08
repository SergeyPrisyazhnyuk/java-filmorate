package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FilmService {

    InMemoryFilmStorage inMemoryFilmStorage;

    public Film saveFilm(Film film) {
        return inMemoryFilmStorage.save(film);
    }

    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.update(film);
    }

    public List<Film> getFilms() {
        return inMemoryFilmStorage.getAll();
    }

    public Film getById(int Id) {
        return inMemoryFilmStorage.get(Id);
    }

    public void deleteById(int Id) {
        inMemoryFilmStorage.deleteById(Id);
    }

    public void putLike(int fId, int uId) {
        inMemoryFilmStorage.putLike(fId, uId);
    }

    public void deleteLike(int fId, int uId) {
        inMemoryFilmStorage.deleteLike(fId, uId);
    }

    public List<Film> getFilmsByCount(int count) {
        return inMemoryFilmStorage.getFilmsByCount(count);
    }

}
