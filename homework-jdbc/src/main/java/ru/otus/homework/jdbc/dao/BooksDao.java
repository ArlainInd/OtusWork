package ru.otus.homework.jdbc.dao;

import ru.otus.homework.jdbc.domain.Book;

import java.util.List;
import java.util.Map;

public interface BooksDao {
    void insert (Book book);

    void update (Book book, Long id);

    Map<Long, Book> getById(Long id);

    Map<Long, Book> getAll();

    Map<Long, Book> getAllBookByAuthorId(Long id);

    Map<Long, Book> getAllBookByGenreId(Long id);

    void deleteById(long id);
}
