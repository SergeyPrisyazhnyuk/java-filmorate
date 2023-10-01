package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreDBStorage;

    public List<Genre> getAllGenres() {
        return genreDBStorage.getAllGenres();
    }

    public Genre getGenreById(Integer id) {
        return genreDBStorage.getGenreById(id);
    }
}
