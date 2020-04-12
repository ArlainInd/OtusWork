package ru.otus.homework.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    private Long bookId;
    private String name;
    private Author author;
    private List<Genre> genres;

}
