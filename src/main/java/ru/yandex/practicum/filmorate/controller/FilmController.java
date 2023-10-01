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
        log.info("Invoke delete method with id = " + id);
        filmService.deleteById(id);
    }


    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        log.info("Invoke putLike method with film id = " + id + " and user id = " + userId);
        filmService.putLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        log.info("Invoke deleteLike method with film id = " + id + "and user id = " + userId);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularZeroCount(@RequestParam(defaultValue = "10") int count) {
        log.info("Invoke getPopular method with count = " + count);
        return filmService.getFilmsByCount(count);
    }

}
