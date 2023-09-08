package ru.yandex.practicum.filmorate.controller;


import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

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
        log.info("Invoke get method to return all films");
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable("id") int id) {
        log.info("Invoke get method to return film by Id");
        return filmService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        filmService.deleteById(id);
    }


    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        filmService.putLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular?count={count}")
    public List<Film> getPopular(@PathVariable("count") int count) {
        return filmService.getFilmsByCount(count);
    }

}
