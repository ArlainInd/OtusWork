package ru.otus.homework.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.mongo.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findAll();

    Optional<Genre> findByName(String name);
}
