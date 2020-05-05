package ru.otus.homework.data.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.homework.data.model.Author;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAll();

    @Transactional
    @Modifying
    @Query("update Author a set a.name = :name, a.birth_date = :date, a.country = :country where a.id =:id")
    int update(@Param("id") Long id, @Param("name") String name, @Param("date") Date birthDate, @Param("country") String country);
}
