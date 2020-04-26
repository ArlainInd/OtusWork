package ru.otus.homework.jpa.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.jpa.model.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class GenreRepositoryImpl implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Boolean update(Genre genre) {
        try {
            Query query = em.createQuery("update Genre g " +
                    "set g.name = :name " +
                    "where g.id = :id");
            query.setParameter("name", genre.getName());
            query.setParameter("id", genre.getId());
            query.executeUpdate();
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean deleteById(Long genreId) {
        try {
            Query query = em.createQuery("delete " +
                    "from Genre g " +
                    "where g.id = :id");
            query.setParameter("id", genreId);
            query.executeUpdate();
            return Boolean.TRUE;
        }
        catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> findById(Long genreId) {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Genre g where g.id = :id"
                , Genre.class);
        query.setParameter("id", genreId);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
