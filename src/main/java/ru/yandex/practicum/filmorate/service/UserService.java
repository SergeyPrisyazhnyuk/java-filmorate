package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    private int userId = 1;

    private final Map<Integer, User> users = new HashMap<>();

    private int setUserId() {
        return userId++;
    }

    public User saveUser(User user) {
        int id = setUserId();
        user.setId(id);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(id, user);
        log.info("Добавлен пользователь с Id: " + user.getId() + " и именем: " + user.getName());
        return user;
    }

    public User updateUser(User user) {
        if (users.get(user.getId()) != null) {
            users.put(user.getId(), user);
            log.info("Обновлен фильм с Id: " + user.getId() + " и названием: " + user.getName());
            return user;
        } else {
            throw new UserNotFoundException("Не найден юзер с id: " + user.getId());
        }
    }

    public List<User> getUsers() {
        return List.copyOf(users.values());
    }

}
