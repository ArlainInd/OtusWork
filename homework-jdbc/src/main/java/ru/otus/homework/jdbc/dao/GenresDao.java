package ru.otus.homework.jdbc.dao;

import ru.otus.homework.jdbc.domain.Genre;

import java.util.List;

public interface GenresDao {
    void insert (Genre genre);

    void update(Long id, String name);

    Genre getById(Long id);

    List<Genre> getAll();

    Boolean deleteById(long id);
}
