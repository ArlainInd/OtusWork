package ru.otus.homework.jpa.repository;

import ru.otus.homework.jpa.model.Comment;

import java.util.List;

public interface CommentRepository {
    Comment SaveComment(Comment comment);

    List<Comment> findAllByBookId(Long bookId);
}
