package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film save(Film film);

    Film update(Film film);

    void deleteById(int Id);

    List<Film> getAll();

    Film get(int Id);

    void putLike(int fId, int uId);

    void deleteLike(int fId, int uId);

    List<Film> getFilmsByCount(int count);

}
