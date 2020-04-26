package ru.otus.homework.jpa.repository;

import ru.otus.homework.jpa.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre save(Genre genre);
    Boolean update(Genre genre);
    Boolean deleteById(Long genreId);
    List<Genre> findAll();
    Optional<Genre> findById(Long genreId);
}
