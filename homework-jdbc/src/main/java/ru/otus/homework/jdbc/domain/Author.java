package ru.otus.homework.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Author {

    public Author(Long authorId) {
        this.authorId = authorId;
    }

    private Long authorId;
    private String name;
    private Date birthDate;
    private String counrty;
    private List<Book> books;
}
