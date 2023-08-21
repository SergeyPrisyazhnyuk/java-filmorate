package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService = new UserService();

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

}
