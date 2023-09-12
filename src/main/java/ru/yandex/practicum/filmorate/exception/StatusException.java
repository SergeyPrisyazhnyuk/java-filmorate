package ru.yandex.practicum.filmorate.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StatusException extends Exception{

    private final HttpStatus httpStatus;

    public StatusException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
