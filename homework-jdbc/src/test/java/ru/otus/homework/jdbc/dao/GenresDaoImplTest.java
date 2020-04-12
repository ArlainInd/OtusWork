package ru.otus.homework.jdbc.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.jdbc.JdbcMainApplication;
import ru.otus.homework.jdbc.domain.Book;
import ru.otus.homework.jdbc.domain.Genre;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GenresDaoImplTest {
    @Autowired
    private GenresDaoImpl genreDao;

    @Autowired
    private BooksDaoImpl booksDao;

    @Test
    public void insertGenreTest() {
        Genre genre = new Genre(104L, "Фантастика");
        genreDao.insert(genre);
        Genre findGenre = genreDao.getById(104L);
        System.out.println(findGenre);
        Assert.assertEquals(findGenre, genre);
    }

    @Test
    public void updateGenre() {
        genreDao.update(100L, "Женский роман");
        Genre genre = genreDao.getById(100L);
        Map<Long, Book> map = booksDao.getById(1L);
        assertThat(genre.getName()).isEqualTo("Женский роман");
        List<Genre> genreList = map.get(1L).getGenres();
        System.out.println(genreList);
        assertThat(genreList.contains(genre)).isTrue();
    }

    @Test
    public void deleteGenreTest() {
        genreDao.deleteById(103L);
        Genre findGenre = genreDao.getById(103L);
        assertThat(findGenre.getGenreId()).isNull();
    }

    @Test
    public void deleteGenreErr() {
        genreDao.deleteById(100L);
        Genre findGenre = genreDao.getById(100L);
        assertThat(findGenre.getGenreId()).isNotNull();
    }

    @Test
    public void findAllTest() {
        List<Genre> genreList = genreDao.getAll();
        System.out.println(genreList.size());
        assertThat(genreList.size()).isGreaterThan(1);
    }

    @Test
    public void findByIdTest() {
        Genre genre = genreDao.getById(100L);
        System.out.println(genre);
        assertThat(genre.getGenreId()).isEqualTo(100L);
    }
}
