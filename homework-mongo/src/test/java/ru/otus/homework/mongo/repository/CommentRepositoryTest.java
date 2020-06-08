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
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    private String bookID;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();

        LocalDate date0 = LocalDate.of(1936, 11, 12);
        Author author = new Author("Роберт Сальваторе", date0, "США", new ArrayList<>());
        Author authorSave = authorRepository.save(author);

        Genre genre = new Genre("Роман");
        Genre genreSave = genreRepository.save(genre);

        Book book = new Book("Темный эльф", authorSave, genreSave);
        Book b1 = bookRepository.save(book);

        bookID = b1.getId();

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Комментарий 1", b1));
        comments.add(new Comment("Комментарий 2", b1));
        comments.add(new Comment("Комментарий 3", b1));

        repository.saveAll(comments);
    }

    @Test
    void findAllByBookTest() {
        Optional<Book> book = bookRepository.findById(bookID);

        List<Comment> comments = repository.findAllByBook(book.get());

        assertThat(comments.size()).isGreaterThan(0);
    }

    @Test
    void findByNameAndBookTest() {
        Optional<Book> book = bookRepository.findById(bookID);

        Optional<Comment> comment = repository.findByNameAndBook("Комментарий 1", book.get());

        assertThat(comment.get().getName()).isEqualTo("Комментарий 1");
        assertThat(comment.get().getBook()).isEqualTo(book.get());
    }

    @Test
    void  deleteByBookTest() {
        Optional<Book> book = bookRepository.findById(bookID);

        repository.deleteByBook(book.get());

        List<Comment> comments = repository.findAllByBook(book.get());

        assertThat(comments.isEmpty()).isTrue();
        assertThat(comments.size()).isEqualTo(0);
    }
}