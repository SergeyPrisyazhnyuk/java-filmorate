package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void test_addFriend() {
        // given
        User user1 = new User()
                .setEmail("a@r.ru")
                .setName("Sergo")
                .setLogin("JavaCoder")
                .setBirthday(LocalDate.parse("1981-06-01"));

        User user2 = new User()
                .setEmail("a2@r.ru")
                .setName("Sergo2")
                .setLogin("JavaCoder2")
                .setBirthday(LocalDate.parse("1981-06-01"));

        userStorage.save(user1);
        userStorage.save(user2);

        userStorage.addFriend(user1.getId(), user2.getId());

        assertAll("Check friend user1",
                () -> assertNotNull(user1.getFriends())
        );

        assertAll("Check friend user2",
                () -> assertNotNull(user2.getFriends())
        );

        int i1 = user1.getFriends().stream().collect(Collectors.toList()).get(0);
        int i2 = user2.getFriends().stream().collect(Collectors.toList()).get(0);

        assertEquals(2, i1);
        assertEquals(1, i2);

    }

}
