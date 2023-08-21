package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validate.MinimumDate;

import java.time.LocalDate;

@Data
@ToString
@Accessors(chain = true)
public class Film {

    Integer id;

    @NotBlank(message = "Название не может быть пустым!")
    String name;

    @Size(max = 200, message = "Максимальная длина описания — 200 символов!")
    String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @MinimumDate
    LocalDate releaseDate;

    @Min(value = 0, message = "Продолжительность фильма должна быть положительной!")
    Long duration;
}
