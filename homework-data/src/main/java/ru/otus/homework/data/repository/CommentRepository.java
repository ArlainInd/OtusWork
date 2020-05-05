package ru.otus.homework.data.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.homework.data.model.Comment;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByBookId(Long BookId);
    List<Comment> findAll();

    @Transactional
    @Modifying
    @Query("update Comment c set c.name = :name where c.id =:id")
    int update(@Param("id") Long id, @Param("name") String name);

}
