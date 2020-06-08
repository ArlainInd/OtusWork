package ru.otus.homework.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByBook(Book book);

    Optional<Comment> findByNameAndBook(String name, Book book);

    @Query(value="{'book' : :#{#book}}", delete = true)
    void deleteByBook (@Param("book") Book book);
}
