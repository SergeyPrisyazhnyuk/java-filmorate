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

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId) {
        log.info("Invoke addFriend method with user id = " + id + " and friend id = " + friendId );
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") int id, @PathVariable("friendId") int friendId) {
        log.info("Invoke deleteFriend method with user id = " + id + " and friend id = " + friendId );
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") int id) {
        log.info("Invoke getFriends method with user id = " + id );
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") int id, @PathVariable("friendId") int otherId) {
        log.info("Invoke getCommonFriends method with user id = " + id + " and other id = " + otherId );
        return userService.getCommonFriends(id, otherId);
    }

}
