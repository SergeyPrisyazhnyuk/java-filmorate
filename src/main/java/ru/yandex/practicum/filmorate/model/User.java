package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ToString
@Accessors(chain = true)
public class User {

    private Integer id;

    @NotBlank(message = "Электронная почта не может быть пустой!")
    @Email(message = "Некорректная эл. почта!")
    private String email;

    @NotBlank(message = "Логин не может быть пустым!")
    @Pattern(regexp = "^\\S*$", message = "Логин не может содержать пробелы!")
    private String login;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Дата рождения не может быть в будущем!")
    private LocalDate birthday;

}
