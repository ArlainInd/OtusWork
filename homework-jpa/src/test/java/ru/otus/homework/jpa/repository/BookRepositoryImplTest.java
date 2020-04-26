package ru.otus.homework.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.jpa.model.Author;
import ru.otus.homework.jpa.model.Book;
import ru.otus.homework.jpa.model.Comment;
import ru.otus.homework.jpa.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование репозитория BookRepository")
@DataJpaTest
@Import({BookRepositoryImpl.class, AuthorRepositoryImpl.class, GenreRepositoryImpl.class})
class BookRepositoryImplTest {

    @Autowired
    BookRepositoryImpl bookRepository;

    @Autowired
    AuthorRepositoryImpl authorRepository;

    @Autowired
    GenreRepositoryImpl genreRepository;

    @Test
    void saveTest() {
        Optional<Author> author = authorRepository.findById(2L);
        Optional<Genre> genre = genreRepository.findById(1L);
        Book book = new Book("Пепел и сталь", author.orElse(null), genre.orElse(null));
        Book bookNew = bookRepository.save(book);
        assertThat(bookNew.getId()).isGreaterThan(1L);
        assertThat(bookNew.getName()).isEqualTo("Пепел и сталь");
    }

    @Test
    void updateTest() {
        Optional<Book> book = bookRepository.findBookById(3L);
        book.orElse(null).setName("666");
        Boolean bol = bookRepository.update(book.orElse(null));
        assertThat(bol).isTrue();
        Optional<Book> bookFind = bookRepository.findBookById(3L);
        assertThat(bookFind.orElseGet(null).getName()).isEqualTo("666");
    }

    @Test
    void deleteByIdTest() {
        Boolean bol = bookRepository.deleteById(4L);
        assertThat(bol).isTrue();
        Optional<Book> bookDel = bookRepository.findBookById(4L);
        assertThat(bookDel).isEmpty();
    }

    @Test
    void findAllTest() {
        List<Book> books = bookRepository.findAll();
        assertThat(books.isEmpty()).isFalse();
        assertThat(books.size()).isGreaterThan(1);
    }

    @Test
    void findBookByIdTest() {
        Optional<Book> book = bookRepository.findBookById(5L);
        assertThat(book.get().getName()).isEqualTo("Игра престолов");
    }
}