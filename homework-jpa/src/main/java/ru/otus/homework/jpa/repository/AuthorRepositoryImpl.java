package ru.otus.homework.jpa.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.jpa.model.Author;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Author saveAuthor(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findById(Long authorId) {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a where a.id = :id"
                , Author.class);
        query.setParameter("id", authorId);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean update(Author author) {
        try {
            Query query = em.createQuery("update Author a " +
                    "set a.name = :name " +
                    "    a.birth_date = : date" +
                    "    a.country = : country" +
                    "where a.id = :id");
            query.setParameter("name", author.getName());
            query.setParameter("date", author.getBirth_date());
            query.setParameter("country", author.getCountry());
            query.setParameter("id", author.getId());
            query.executeUpdate();
            return Boolean.TRUE;
        }
        catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean deleteById(long id) {
        try {
            Query query = em.createQuery("delete " +
                    "from Author a " +
                    "where a.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            return Boolean.TRUE;
        }
        catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
