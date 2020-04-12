package ru.otus.homework.jdbc.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.jdbc.dao.util.GenreMapper;
import ru.otus.homework.jdbc.domain.Genre;

import java.util.List;

@AllArgsConstructor
@Repository
public class GenresDaoImpl implements GenresDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(Genre genre) {
        Genre genreFind = getById(genre.getGenreId());
        if (genreFind.getGenreId() == null) {
            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue("id", genre.getGenreId());
            in.addValue("name", genre.getName());

            namedParameterJdbcOperations.update("insert into genres (genreId, name) values (:id, :name)", in);
        }
        else {
            System.out.println("такой жанр уже существует");
        }
    }

    @Override
    public void update(Long id, String name) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("id", id);
        in.addValue("name", name);
        namedParameterJdbcOperations.update("update genres set name = :name where genreId = :id", in);
    }

    @Override
    public Genre getById(Long id) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("id",id);
        Genre genreNew = new Genre();
        try {
            genreNew = namedParameterJdbcOperations.queryForObject("select * from genres where genreId = :id",
                    in, new GenreMapper());
        }
        catch (Exception e) {}
        return genreNew;
    }

    @Override
    public List<Genre> getAll() {
        return  namedParameterJdbcOperations.query("select * from genres", new GenreMapper());
    }

    @Override
    public Boolean deleteById(long id) {
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("id",id);
        try {
            namedParameterJdbcOperations.update(
                    "delete from genres where genreId = :id", in);
            return Boolean.TRUE;
        }
        catch (Exception e) {
            System.out.println("Ошибка при удалении жанра - " + e.getMessage());
            return Boolean.FALSE;
        }
    }
}
