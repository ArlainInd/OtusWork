package ru.otus.homework.jpa.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.jpa.model.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Boolean update(Book book) {
        try {
            Query query = em.createQuery("update Book b " +
                    "set b.name = :name " +
                    "where b.id = :id");
            query.setParameter("name", book.getName());
            query.setParameter("id", book.getId());
            query.executeUpdate();
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean deleteById(Long bookId) {
        try {
            Query query = em.createQuery("delete " +
                    "from Book b " +
                    "where b.id = :id");
            query.setParameter("id", bookId);
            query.executeUpdate();
            return Boolean.TRUE;
        }
        catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public Optional<Book> findBookById(long bookId) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.id = :id"
                , Book.class);
        query.setParameter("id", bookId);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
