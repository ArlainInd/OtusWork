package ru.otus.homework.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    public Genre(Long genreId) {
        this.genreId = genreId;
    }

    private Long genreId;
    private String Name;
}
