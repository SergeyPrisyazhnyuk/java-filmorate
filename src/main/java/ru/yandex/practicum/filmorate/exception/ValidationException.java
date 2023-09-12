package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;

@Getter
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
