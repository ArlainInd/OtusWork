package ru.otus.homework.jpa.repository;

import ru.otus.homework.jpa.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Boolean update(Book book);
    Boolean deleteById(Long bookId);
    List<Book> findAll();
    Optional<Book> findBookById(long bookId);
}
