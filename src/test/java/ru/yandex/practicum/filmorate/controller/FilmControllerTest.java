package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
public class FilmControllerTest {
    private final FilmController filmController;

    @Test
    void test_Save() {
        // given
        Film film = new Film().setName("FilmName")
                .setDescription("My Film Description")
                .setDuration(1000L)
                .setReleaseDate(LocalDate.parse("2020-10-10"));


        // when
        Film result = filmController.save(film);

        // then
        assertAll("Check id's field",
                () -> assertNotNull(result.getId())
        );
    }


}
