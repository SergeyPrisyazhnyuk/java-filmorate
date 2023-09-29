package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class FilmGenre {

    private Integer filmGenreId;
    private Integer filmId;
    private Integer genreId;

}
