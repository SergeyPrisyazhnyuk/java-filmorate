package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User save(@Valid @RequestBody User user) {
        log.info("Invoke save method with user = {}", user);
        return userService.saveUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Invoke update method with user = {}", user);
        return userService.updateUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Invoke get method");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") int id) {
        log.info("Invoke get method to return film by Id");
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        userService.deleteById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(int id, int friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(int id, int friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(int id) {
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(int id, int otherId) {
        return userService.getCommonFriends(id, otherId);
    }

}
