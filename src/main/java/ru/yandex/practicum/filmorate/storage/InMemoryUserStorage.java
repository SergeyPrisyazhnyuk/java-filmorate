package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private int userId = 1;

    private final Map<Integer, User> users = new HashMap<>();

    private int setUserId() {
        return userId++;
    }

    @Override
    public User save(User user) {
        int id = setUserId();
        user.setId(id);
        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        users.put(id, user);
        log.info("Добавлен пользователь с Id: " + user.getId() + " и именем: " + user.getName());
        return user;
    }

    @Override
    public User update(User user) {
        if (users.get(user.getId()) != null) {
            users.put(user.getId(), user);
            log.info("Обновлен фильм с Id: " + user.getId() + " и названием: " + user.getName());
            return user;
        } else {
            throw new UserNotFoundException("Не найден юзер с id: " + user.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        if (users.get(id) != null) {
            users.remove(id);
        } else {
            throw new UserNotFoundException("Не найден юзер с id: " + id);
        }
    }

    @Override
    public List<User> getAll() {
        return List.copyOf(users.values());
    }

    @Override
    public User get(int id) {
        if (users.get(id) != null) {
            return users.get(id);
        } else {
            throw new UserNotFoundException("Не найден юзер с id: " + id);
        }
    }

    @Override
    public void addFriend(int id, int friendId) {
        if (users.get(id) != null && users.get(friendId) != null) {
            users.get(id).addFriend(friendId);
            users.get(friendId).addFriend(id);
        } else if (users.get(id) == null) {
            throw new UserNotFoundException("Не найден юзер с id: " + id);
        } else if (users.get(friendId) == null) {
            throw new UserNotFoundException("Не найден друг с id: " + id);
        } else {
            throw new UserNotFoundException("Не найдены ни друг с id: " + id + ", ни юзер с id: " + id);
        }
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        if (users.get(id) != null && users.get(friendId) != null) {
            users.get(id).deleteFriend(friendId);
            users.get(friendId).deleteFriend(id);
        } else if (users.get(id) == null) {
            throw new UserNotFoundException("Не найден юзер с id: " + id);
        } else if (users.get(friendId) == null) {
            throw new UserNotFoundException("Не найден друг с id: " + id);
        } else {
            throw new UserNotFoundException("Не найдены ни друг с id: " + id + ", ни юзер с id: " + id);
        }

    }

    @Override
    public List<User> getFriends(int id) {
        User user = users.get(id);

        if (user != null) {
            return user.getFriends().stream()
                    .map(this::get)
                    .collect(Collectors.toList());
        } else {
            throw new UserNotFoundException("Не найден юзер с id: " + id);
        }
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        List<User> friends = getFriends(id);
        List<User> otherFriends = getFriends(otherId);
        List<User> common = new ArrayList<>(friends);
        common.retainAll(otherFriends);
        return common;
    }
}
