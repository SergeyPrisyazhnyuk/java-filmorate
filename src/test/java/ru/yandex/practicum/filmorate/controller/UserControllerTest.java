package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
public class UserControllerTest {

    private final UserController userController;

    @Test
    void test_Save() {
        // given
        User user = new User()
                .setEmail("a@r.ru")
                .setName("Sergo")
                .setLogin("JavaCoder")
                .setBirthday(LocalDate.parse("1981-06-01"));


        // when
        User result = userController.save(user);

        // then
        assertAll("Check id's field",
                () -> assertNotNull(result.getId())
        );
    }

}
