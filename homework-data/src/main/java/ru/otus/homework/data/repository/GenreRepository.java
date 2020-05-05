package ru.otus.homework.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.homework.data.model.Genre;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
}
