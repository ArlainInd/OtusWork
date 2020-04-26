package ru.otus.homework.jpa.repository;

import ru.otus.homework.jpa.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Boolean deleteById(Long commentId);
    Boolean update(Comment comment);

    List<Comment> findAllByBookId(Long bookId);
    List<Comment> findAll();
    Optional<Comment> findById(Long commentId);
}
