package ru.otus.homework.data.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.data.model.Author;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findAllTest() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors.isEmpty()).isFalse();
        assertThat(authors.size()).isGreaterThan(1);
    }

    @Test
    public void  updateTest() {
        Optional<Author> authorOld = authorRepository.findById(4L);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate date1 = LocalDate.of(1991, 5, 28);
        Date date = Date.from(date1.atStartOfDay(defaultZoneId).toInstant());
        authorRepository.update(4L, "Тест Тестович", date, "Россия");
        Optional<Author> authorNew = authorRepository.findById(4L);
        assertThat(authorOld).isNotEqualTo(authorNew);
        assertThat(authorNew.get().getName()).isEqualTo("Тест Тестович");
    }

    @Test
    public void findByIdTest() {
        Optional<Author> author = authorRepository.findById(5L);
        assertThat(author.isPresent()).isTrue();
        assertThat(author.get().getName()).isEqualTo("Джордж Мартин");
    }

    @Test
    public void deleteById() {
        authorRepository.deleteById(6L);
        Optional<Author> author = authorRepository.findById(6L);
        assertThat(author.isPresent()).isFalse();
    }

    @Test
    public void saveTest() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate date1 = LocalDate.of(1921, 11, 12);
        Date date = Date.from(date1.atStartOfDay(defaultZoneId).toInstant());
        Author author = new Author("Брэндон Сандерсон", date , "США", new ArrayList<>(0));
        Author authorNew = authorRepository.save(author);
        assertThat(authorNew.getName()).isEqualTo("Брэндон Сандерсон");
        assertThat(authorNew.getId()).isGreaterThan(1L);
    }
}