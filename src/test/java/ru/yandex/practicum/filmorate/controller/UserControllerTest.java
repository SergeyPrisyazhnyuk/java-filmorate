package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerTest {

    private final InMemoryUserStorage userStorage = new InMemoryUserStorage();

    @Test
    void test_Save() {
        // given
        User user = new User()
                .setEmail("a@r.ru")
                .setName("Sergo")
                .setLogin("JavaCoder")
                .setBirthday(LocalDate.parse("1981-06-01"));


        System.out.println(user.toString());

        // when
        User result = userStorage.save(user);

        // then
        assertAll("Check id's field",
                () -> assertNotNull(result.getId())
        );
    }

}
