package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validate.MinimumDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Film {

    private Integer id;

    @NotBlank(message = "Название не может быть пустым!")
    private String name;

    @Size(max = 200, message = "Максимальная длина описания — 200 символов!")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @MinimumDate
    private LocalDate releaseDate;

    @Min(value = 0, message = "Продолжительность фильма должна быть положительной!")
    private Long duration;

    private Rating rating;

    private final Set<Integer> likes = new HashSet<>();
    private List<Genre> genreList = new ArrayList<>();

/*
    public Film(String name, String description, LocalDate releaseDate, Long duration, Rating rating) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rating = rating;
    }

    public Film(String name, String description, LocalDate releaseDate, Long duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }*/

    public void addLike(int id) {
        likes.add(id);
    }

    public void deleteLike(int id) {
        likes.remove(id);
    }

    public int getLikesCount() {
        return likes.size();
    }
}
