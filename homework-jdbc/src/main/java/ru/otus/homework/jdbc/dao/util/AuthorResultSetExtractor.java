package ru.otus.homework.jdbc.dao.util;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.homework.jdbc.domain.Author;
import ru.otus.homework.jdbc.domain.Book;
import ru.otus.homework.jdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthorResultSetExtractor implements ResultSetExtractor<Map<Long, Author>> {
    @Override
    public Map<Long, Author> extractData(ResultSet rs) throws SQLException,
            DataAccessException {

        Map<Long, Author> authors = new HashMap<>();
        while (rs.next()) {
            long id = rs.getLong("author_id");

            Author author = authors.get(id);
            if (author == null) {
                author = new Author(id,
                        rs.getString("author_name"),
                        rs.getDate("author_birthdate"),
                        rs.getString("author_country"),
                        new ArrayList<>());
                authors.put(author.getAuthorId(), author);
            }
            author.getBooks().add(new Book(rs.getLong("book_id"),
                    rs.getString("book_name"),
                    new Author(id),
                    new ArrayList<>()
            ));

        }
        return authors;
    }
}
