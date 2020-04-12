package ru.otus.homework.jdbc.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.jdbc.JdbcMainApplication;
import ru.otus.homework.jdbc.domain.Author;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JdbcMainApplication.class)
public class AuthorsDaoImplTest {

    @Autowired
    AuthorsDaoImpl authorsDao;

    @Test
    public void getAllAuthorsTest() {
        Map<Long, Author> authors = authorsDao.getAll();
        System.out.println(authors);
        assertThat(authors.isEmpty()).isFalse();
        assertThat(authors.get(1000L).getName()).isEqualTo("Лев Толстой");
    }

    @Test
    public void  getAuthorByIdTest() {
        Map<Long, Author> author1 = authorsDao.getById(1000L);
        System.out.println(author1);
        assertThat(author1.isEmpty()).isFalse();
        assertThat(author1.get(1000L).getName()).isEqualTo("Лев Толстой");
    }

    @Test
    public void insertAuthorTest() throws ParseException {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate date1 = LocalDate.of(1921, 11, 12);
        Date date = Date.from(date1.atStartOfDay(defaultZoneId).toInstant());
        Author author = new Author(3000L, "Станислав Лем", date, "Польша", new ArrayList<>());
        authorsDao.insert(author);
        Map<Long, Author> author1 = authorsDao.getById(3000L);
        System.out.println(author1);
        assertThat(author1.isEmpty()).isFalse();
        assertThat(author1.get(3000L).getName()).isEqualTo("Станислав Лем");
    }

    @Test
    public void updateAuthorTest() {
        Author author = new Author(1000L, "Станислав Лем", null, "Польша", new ArrayList<>());
        authorsDao.update(author, 1000L);
        Map<Long, Author> author1 = authorsDao.getById(1000L);
        System.out.println(author1);
        assertThat(author1.isEmpty()).isFalse();
        assertThat(author1.get(1000L).getName()).isEqualTo("Станислав Лем");
        assertThat(author1.get(1000L).getCounrty()).isEqualTo("Польша");
    }

    @Test
    public void deleteAuthorTest() {
        authorsDao.deleteById(3001L);
        Map<Long, Author> author1 = authorsDao.getById(3001L);
        System.out.println(author1);
        assertThat(author1.isEmpty()).isTrue();

    }

    @Test
    public void deleteAuthorErrTest() {
        authorsDao.deleteById(1000L);
        Map<Long, Author> author1 = authorsDao.getById(1000L);
        System.out.println(author1);
        assertThat(author1.isEmpty()).isFalse();

    }
}
