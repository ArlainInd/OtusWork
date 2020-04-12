package ru.otus.homework.jdbc.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.jdbc.dao.util.BookResultSetExtractor;
import ru.otus.homework.jdbc.domain.Author;
import ru.otus.homework.jdbc.domain.Book;
import ru.otus.homework.jdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
@Repository
public class BooksDaoImpl implements BooksDao {
    private static final String SQL_BOOK =
            "select b.bookId as id, " +
                    "b.name as book_name, " +
                    "a.authorId as author_id, " +
                    "a.name as author_name, " +
                    "a.birthDate as author_birthdate, " +
                    "a.country as author_country, " +
                    "g.genreId as genre_id, " +
                    "g.name as genre_name " +
            "  from books b" +
            " inner join authors a" +
                    " on a.authorId = b.authorId" +
            " inner join books_genres bg" +
                    " on bg.book_id = b.bookId" +
            " inner join genres g " +
                    " on g.genreId = bg.genre_id ";

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;



    @Override
    public void insert(Book book) {
        try {
            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue("id", book.getBookId());
            in.addValue("name", book.getName());
            in.addValue("author", book.getAuthor().getAuthorId());
            namedParameterJdbcOperations.update("insert into books (bookid, name, authorid) values (:id, :name, :author)", in);

            book.getGenres().forEach(genre -> {
                MapSqlParameterSource in2 = new MapSqlParameterSource();
                in2.addValue("book_id", book.getBookId());
                in2.addValue("genre_id", genre.getGenreId());
                namedParameterJdbcOperations.update("insert into books_genres (book_id, genre_id) values (:book_id, :genre_id)", in2);
            });

        }
        catch (Exception e){
            System.out.println("Не удалось добавить книгу. Ошибка - " + e.getMessage());
        }
    }

    @Override
    public void update(Book book, Long id) {
        try {
            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue("id", id);
            in.addValue("name", book.getName());
            in.addValue("author", book.getAuthor().getAuthorId());
            namedParameterJdbcOperations.update("update books set name = :name, authorId = :author where bookId = :id", in);
        }
        catch (Exception e) {
            System.out.println("Не удалось обновить книгу. Ошибка - " + e.getMessage());
        }
    }

    @Override
    public Map<Long, Book> getById(Long id) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("id",id);
        Map<Long, Book>  book = new HashMap<>();
        try {
            book = namedParameterJdbcOperations.query(SQL_BOOK + " where b.bookId = :id",
                    in, new BookResultSetExtractor());
        }
        catch (Exception e) {}
        return book;
    }

    @Override
    public Map<Long, Book> getAll() {
        return namedParameterJdbcOperations.query(SQL_BOOK, new BookResultSetExtractor());
    }

    @Override
    public Map<Long, Book> getAllBookByAuthorId(Long id) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("id",id);
        return namedParameterJdbcOperations.query(SQL_BOOK + " where a.authorid = :id", in, new BookResultSetExtractor());
    }

    @Override
    public Map<Long, Book> getAllBookByGenreId(Long id) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("id",id);
        return namedParameterJdbcOperations.query(SQL_BOOK + "where g.genreId = :id", in, new BookResultSetExtractor());
    }

    @Override
    public void deleteById(long id) {
        try {
            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue("id", id);
            namedParameterJdbcOperations.update(
                    "delete from books where bookId = :id", in);
        }
        catch (Exception e) {
            System.out.println("Не удалось удалить книгу. Ошибка - " + e.getMessage());
        }
    }
}
