package ru.otus.homework.mongo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.mongo.model.Author;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.model.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();

        LocalDate date0 = LocalDate.of(1936, 11, 12);
        Author author = new Author("Роберт Сальваторе", date0, "США", new ArrayList<>());
        Author authorSave = authorRepository.save(author);

        Genre genre = new Genre("Роман");
        Genre genreSave = genreRepository.save(genre);

        Book book = new Book("Темный эльф", authorSave, genreSave);
        Book b1 = repository.save(book);

    }

    @Test
    void findAllTest() {
        List<Book> books = repository.findAll();
        assertThat(books.size()).isGreaterThan(0);
    }


    @Test
    void findByNameAndAuthorTest() {
        LocalDate date0 = LocalDate.of(1936, 11, 12);
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween("Роберт Сальваторе", date0.minusDays(1), date0.plusDays(1));

        Optional<Book> book = repository.findByNameAndAuthor("Темный эльф", author.get());

        assertThat(book.get().getName()).isEqualTo("Темный эльф");
        assertThat(book.get().getAuthor()).isEqualTo(author.get());
    }

    @Test
    void findAllByAuthorTest() {
        LocalDate date0 = LocalDate.of(1936, 11, 12);
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween("Роберт Сальваторе", date0.minusDays(1), date0.plusDays(1));
        Optional<Genre> genre = genreRepository.findByName("Роман");

        Book book = new Book("Долина ледяного ветра", author.get(), genre.get());
        repository.save(book);

        List<Book> books = repository.findAllByAuthor(author.get());
        assertThat(books.size()).isEqualTo(2);
    }
}