package ru.otus.homework.jpa.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
            Query query = em.createQuery("delete " +
                    "from Comment c " +
                    "where c.id = :id");
            query.setParameter("id", commentId);
            query.executeUpdate();
            return Boolean.TRUE;
        }
        catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean update(Comment comment) {
        try {
            Query query = em.createQuery("update Comment c " +
                    "set c.name = :name " +
                    "where c.id = :id");
            query.setParameter("name", comment.getName());
            query.setParameter("id", comment.getId());
            query.executeUpdate();
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :id", Comment.class);
        query.setParameter("id", bookId);
        return query.getResultList();
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.id = :id"
                , Comment.class);
        query.setParameter("id", commentId);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
