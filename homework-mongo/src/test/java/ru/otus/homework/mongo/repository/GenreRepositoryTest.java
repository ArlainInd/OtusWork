package ru.otus.homework.mongo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.mongo.model.Author;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.model.Comment;
import ru.otus.homework.mongo.model.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();

        List<Genre> genres = new ArrayList<>();
        Genre genre = new Genre("Роман");
        genres.add(genre);
        Genre genre2 = new Genre("Повесть");
        genres.add(genre2);
        repository.saveAll(genres);
    }

    @Test
    void findAll() {
        List<Genre> genres = repository.findAll();
        assertThat(genres.size()).isEqualTo(2);
    }

    @Test
    void findByName() {
        Optional<Genre> genre = repository.findByName("Роман");
        assertThat(genre.get().getName()).isEqualTo("Роман");
    }
}