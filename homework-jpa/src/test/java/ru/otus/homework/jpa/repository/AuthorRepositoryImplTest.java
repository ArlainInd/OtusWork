package ru.otus.homework.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.jpa.model.Author;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование репозитория AuthorRepository")
@DataJpaTest
@Import({BookRepositoryImpl.class, AuthorRepositoryImpl.class})
class AuthorRepositoryImplTest {

    @Autowired
    BookRepositoryImpl bookRepository;

    @Autowired
    AuthorRepositoryImpl authorRepository;

    @Test
    void saveAuthor() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate date1 = LocalDate.of(1921, 11, 12);
        Date date = Date.from(date1.atStartOfDay(defaultZoneId).toInstant());
        Author author = new Author("Брэндон Сандерсон", date , "США", new ArrayList<>(0));
        Author authorNew = authorRepository.saveAuthor(author);
        assertThat(authorNew.getName()).isEqualTo("Брэндон Сандерсон");
        assertThat(authorNew.getId()).isGreaterThan(1L);
    }

    @Test
    void findAll() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors.isEmpty()).isFalse();
        assertThat(authors.size()).isGreaterThan(1);
    }

    @Test
    void findById() {
        Optional<Author> author = authorRepository.findById(4L);
        assertThat(author.orElse(null).getName()).isEqualTo("Терри Гудкайнд");
        assertThat(author.orElse(null).getId()).isEqualTo(4L);
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
        Boolean bol = authorRepository.deleteById(3L);
        assertThat(bol).isTrue();
        Optional<Author> author = authorRepository.findById(3L);
        assertThat(author).isEmpty();
    }
}