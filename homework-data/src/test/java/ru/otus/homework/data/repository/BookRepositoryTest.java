package ru.otus.homework.data.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.data.model.Author;
import ru.otus.homework.data.model.Book;
import ru.otus.homework.data.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    GenreRepository genreRepository;

    @Test
    public void saveTest() {
        Optional<Author> author = authorRepository.findById(2L);
        Optional<Genre> genre = genreRepository.findById(1L);
        Book book = new Book("Пепел и сталь", author.get(), genre.get());
        Book bookNew = bookRepository.save(book);
        assertThat(bookNew.getId()).isGreaterThan(1L);
        assertThat(bookNew.getName()).isEqualTo("Пепел и сталь");
    }

    @Test
    public void deleteByIdTest() {
        bookRepository.deleteById(4L);
        Optional<Book> bookDel = bookRepository.findById(4L);
        assertThat(bookDel).isEmpty();
    }

    @Test
    public void findAllTest() {
        List<Book> books = bookRepository.findAll();
        assertThat(books.isEmpty()).isFalse();
        assertThat(books.size()).isGreaterThan(1);
    }

    @Test
    public void findBookByIdTest() {
        Optional<Book> book = bookRepository.findById(5L);
        assertThat(book.get().getName()).isEqualTo("Игра престолов");
    }
}