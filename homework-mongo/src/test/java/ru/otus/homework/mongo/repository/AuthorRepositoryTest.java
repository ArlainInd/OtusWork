package ru.otus.homework.mongo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.mongo.model.Author;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        LocalDate date0 = LocalDate.of(1936, 11, 12);
        Author author = new Author("Роберт Сальваторе", date0, "США", new ArrayList<>());
        Author authorSave = repository.save(author);
    }

    @Test
    public void saveAuthorTest() {
        LocalDate date0 = LocalDate.of(1945, 8, 12);
        Author author = new Author("Майкл Муркок", date0, "Англия", new ArrayList<>());
        Author authorSave = repository.save(author);

        Optional<Author> author1 = repository.findById(authorSave.getId());

        assertThat(author.equals(author1.get())).isTrue();
    }

    @Test
    public void findAuthorTest() {
        LocalDate date0 = LocalDate.of(1936, 11, 12);

        Optional<Author> author1 = repository.findByNameAndBirthDateBetween("Роберт Сальваторе", date0.minusDays(1), date0.plusDays(1));

        assertThat(author1).isNotEmpty();
        assertThat(author1.get().getBirthDate().equals(date0));
        assertThat(author1.get().getName().equals("Роберт Сальваторе"));
    }
}