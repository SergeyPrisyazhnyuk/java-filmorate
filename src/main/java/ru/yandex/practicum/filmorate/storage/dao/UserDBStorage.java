package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Primary
public class UserDBStorage implements UserStorage {

    int id = 1;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer setIdReturn() {
        return id++;
    }

    @Override
    public User save(User user) {
        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        user.setId(setIdReturn());
        String sql = "INSERT INTO USERS (EMAIL, LOGIN, USER_NAME, BIRTHDAY) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        log.info("Добавлен пользователь с Id: " + user.getId() + " и именем: " + user.getName());
        return user;
    }

    @Override
    public User update(User user) {

        try {
            get(user.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Не был найден юзер с Id = " + user.getId());
        }

        String sqlUpdate = "UPDATE USERS SET EMAIL = ?, LOGIN = ?, USER_NAME = ?, BIRTHDAY = ? WHERE USER_ID = ?";
        jdbcTemplate.update(sqlUpdate, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        log.info("Обновлен пользователь с Id: " + user.getId() + " и названием: " + user.getName());

        return user;

    }

    @Override
    public void deleteById(int id) {
        User user = get(id);

        if (user.getId() != null) {
            String sql = "DELETE FROM USERS WHERE USER_ID = ?";
            jdbcTemplate.update(sql, id);
            log.info("Удален юзер с Id: " + user.getId());
        } else {
            throw new UserNotFoundException("Не найден юзер с id: " + id);
        }
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT USER_ID, EMAIL, USER_NAME, LOGIN, BIRTHDAY FROM USERS";
        try {
            return new ArrayList<>(jdbcTemplate.query(sql, this::mapRowToUser));
        } catch (Exception e) {
            throw new NotFoundException("No Users were found while running getAll()");
        }
    }

    @Override
    public User get(int id) {

        String sql = "SELECT USER_ID, EMAIL, LOGIN, USER_NAME, BIRTHDAY FROM USERS WHERE USER_ID = ?";

        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToUser, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Не был найден юзер с Id = " + id);
        }

    }

    @Override
    public void addFriend(int id, int friendId) {

        User user = get(id);
        User friend = get(friendId);

        if (user.getId() != null && friend.getId() != null) {
            String sql = "INSERT INTO FRIENDS (USER_ID, FRIEND_ID, STATUS) VALUES (?,?,?)";
            jdbcTemplate.update(sql, user.getId(), friend.getId(), "true");
        } else if (user.getId() == null) {
            throw new UserNotFoundException("Не найден юзер с id: " + id);
        } else if (friend.getId() == null) {
            throw new UserNotFoundException("Не найден друг с id: " + id);
        } else {
            throw new UserNotFoundException("Не найдены ни друг с id: " + id + ", ни юзер с id: " + id);
        }
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        User user = get(id);
        User friend = get(friendId);

        if (user.getId() != null && friend.getId() != null) {
            String sql = "DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?";
            jdbcTemplate.update(sql, user.getId(), friend.getId());
        } else if (user.getId() == null) {
            throw new UserNotFoundException("Не найден юзер с id: " + id);
        } else if (friend.getId() == null) {
            throw new UserNotFoundException("Не найден друг с id: " + id);
        } else {
            throw new UserNotFoundException("Не найдены ни друг с id: " + id + ", ни юзер с id: " + id);
        }
    }

    @Override
    public List<User> getFriends(int id) {

        String sql = "SELECT u.* FROM USERS u JOIN FRIENDS f ON u.USER_ID = f.FRIEND_ID WHERE f.USER_ID = ?";
        return new ArrayList<>(jdbcTemplate.query(sql, this::mapRowToUser, id));
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {

        String sql = "SELECT u.* FROM USERS u \n" +
                "\t\tJOIN FRIENDS f ON u.USER_ID = f.FRIEND_ID\n" +
                "\t\tJOIN FRIENDS f2 ON u.USER_ID = f2.FRIEND_ID \n" +
                "\t\t\tWHERE f.USER_ID = ? AND f2.USER_ID = ?";

        return new ArrayList<>(jdbcTemplate.query(sql, this::mapRowToUser, id, otherId));
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return User
                .builder()
                .id(rs.getInt("USER_ID"))
                .email(rs.getString("EMAIL"))
                .login(rs.getString("LOGIN"))
                .name(rs.getString("USER_NAME"))
                .birthday(rs.getDate("BIRTHDAY").toLocalDate())
                .build();
    }
}
