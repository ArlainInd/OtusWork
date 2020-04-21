package ru.otus.homework.jpa.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.jpa.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Comment SaveComment(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
        /*Query query = em.createQuery("insert into books_comments(name, book_id, author_id) values (:name, :author, :book) ");
        query.setParameter("name", comment.getName());
        query.setParameter("author", comment.getAuthor().getId());
        query.setParameter("book", comment.getBook().getId());
        query.executeUpdate();
        return comment;*/
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :id", Comment.class);
        query.setParameter("id", bookId);
        return query.getResultList();
    }
}
