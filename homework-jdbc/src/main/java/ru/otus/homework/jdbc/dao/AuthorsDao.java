package ru.otus.homework.jdbc.dao;

import ru.otus.homework.jdbc.domain.Author;

import java.util.Map;

public interface AuthorsDao {
    void insert (Author author);
    void update (Author author, Long authorId);
    void deleteById (Long authorId);

    Map<Long, Author> getAll();
    Map<Long, Author> getById(Long authorId);
}
