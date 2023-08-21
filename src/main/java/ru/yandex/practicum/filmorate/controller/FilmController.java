package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService = new FilmService();

    @PostMapping
    public Film save(@Valid @RequestBody Film film) {
        log.info("Invoke save method with film = {}", film);
        return filmService.saveFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Invoke update method with film = {}", film);
        return filmService.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Invoke get method");
        return filmService.getFilms();
    }

}
