package ru.otus.homework.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.mongo.model.Author;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findAll();

    Optional<Book> findByNameAndAuthor(String name, Author author);

    List<Book> findAllByAuthor(Author author);

    List<Book> findAllByGenre(Genre genre);

}
