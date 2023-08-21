package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ToString
@Accessors(chain = true)
public class User {

    Integer id;

    @NotBlank(message = "Электронная почта не может быть пустой!")
    @Email(message = "Некорректная эл. почта!")
    String email;

    @NotBlank(message = "Логин не может быть пустым!")
    @Pattern(regexp = "^\\S*$", message = "Логин не может содержать пробелы!")
    String login;

    String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Дата рождения не может быть в будущем!")
    LocalDate birthday;

}
