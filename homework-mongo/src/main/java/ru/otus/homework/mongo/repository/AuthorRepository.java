package ru.otus.homework.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.mongo.model.Author;

import java.time.LocalDate;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByNameAndBirthDateBetween(String name, LocalDate birthDate, LocalDate birthDate2);
}
