package ru.otus.homework.jpa.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.jpa.model.Book;
import ru.otus.homework.jpa.model.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Boolean deleteById(Long commentId) {
        try {
            Comment comment = em.find(Comment.class, commentId);
            em.remove(comment);
            return Boolean.TRUE;
        }
        catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean update(Comment comment) {
        try {
            em.merge(comment);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        Book book = em.find(Book.class, bookId);
        List<Comment> comments = book.getComments();
        return comments;
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return Optional.ofNullable(em.find(Comment.class, commentId));
    }

}
