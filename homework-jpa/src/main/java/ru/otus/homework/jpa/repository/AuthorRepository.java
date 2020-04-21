package ru.otus.homework.jpa.repository;

import ru.otus.homework.jpa.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author saveAuthor(Author author);
    List<Author> findAll();
    Optional<Author> findById(Long authorId);
    Boolean updateNameById(long id, String name);
    Boolean deleteById(long id);
}
