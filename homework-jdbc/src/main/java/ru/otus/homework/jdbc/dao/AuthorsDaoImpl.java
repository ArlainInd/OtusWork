package ru.otus.homework.jdbc.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.jdbc.dao.util.AuthorResultSetExtractor;
import ru.otus.homework.jdbc.domain.Author;

import java.util.Map;

@AllArgsConstructor
@Repository
public class AuthorsDaoImpl implements AuthorsDao {

    private static final String SQL_AUTHOR =
            "select a.authorId as author_id, " +
                    "a.name as author_name, " +
                    "a.birthDate as author_birthdate, " +
                    "a.country as author_country, " +
                    "b.bookId as book_id, " +
                    "b.name as book_name " +
            "  from authors a" +
            "  left join books b" +
                    " on b.authorId = a.authorId";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(Author author) {
        try {
            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue("id", author.getAuthorId());
            in.addValue("name", author.getName());
            in.addValue("birthdate", author.getBirthDate());
            in.addValue("country", author.getCounrty());
            namedParameterJdbcOperations.update("insert into authors (authorid, name, country, birthdate) values (:id, :name, :country, :birthdate)", in);
        }
        catch (Exception e) {
            System.out.println("Не удалось добавить автора. Ошибка - " + e.getMessage());
        }
    }

    @Override
    public void update(Author author, Long authorId) {
        try {
            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue("id", author.getAuthorId());
            in.addValue("name", author.getName());
            in.addValue("birthdate", author.getBirthDate());
            in.addValue("country", author.getCounrty());
            namedParameterJdbcOperations.update("update authors set name = :name, birthdate = :birthdate, country = :country" +
                    " where authorId = :id", in);
        }
        catch (Exception e) {
            System.out.println("Не удалось изменить автора. Ошибка - " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long authorId) {
        try {
            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue("id", authorId);
            namedParameterJdbcOperations.update("delete from authors " +
                    " where authorId = :id", in);
        }
        catch (Exception e) {
            System.out.println("Не удалось удалить автора. Ошибка - " + e.getMessage());
        }
    }

    @Override
    public Map<Long, Author> getAll() {
        Map<Long, Author> authors = namedParameterJdbcOperations.query(SQL_AUTHOR, new AuthorResultSetExtractor());
        return authors;
    }

    @Override
    public Map<Long, Author> getById(Long authorId) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("id", authorId);
        Map<Long, Author> authors = namedParameterJdbcOperations.query(SQL_AUTHOR + " where a.authorId = :id", in,
                new AuthorResultSetExtractor());
        return authors;
    }
}
