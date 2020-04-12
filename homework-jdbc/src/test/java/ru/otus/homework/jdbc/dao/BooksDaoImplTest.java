package ru.otus.homework.jdbc.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.jdbc.JdbcMainApplication;
import ru.otus.homework.jdbc.domain.Author;
import ru.otus.homework.jdbc.domain.Book;
import org.junit.Assert;
import ru.otus.homework.jdbc.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JdbcMainApplication.class)
public class BooksDaoImplTest {

    @Autowired
    private BooksDaoImpl booksDao;

    @Test
    public void insertTest() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(100L));
        Book book = new Book(50L, "Тестовая книга", new Author(1000L), genres);
        booksDao.insert(book);
        Map<Long, Book> findBook = booksDao.getById(50L);
        System.out.println(findBook);
        Assert.assertEquals(findBook.get(50L).getBookId(), book.getBookId());
        Assert.assertEquals(findBook.get(50L).getName(), book.getName());
        Assert.assertEquals(findBook.get(50L).getAuthor().getAuthorId(), book.getAuthor().getAuthorId());
    }

    @Test
    public void getByIdTest() {
        Map<Long, Book> book = booksDao.getById(2L);
        System.out.println(book);
        assertThat(book.get(2L).getName()).isEqualTo("Игра престолов");
    }

    @Test
    public void getAllBookByGenreIdTest() {
        Map<Long, Book> books = booksDao.getAllBookByGenreId(100L);
        System.out.println(books);
        assertThat(books.isEmpty()).isEqualTo(Boolean.FALSE);
    }

    @Test
    public void getAllBookByAuthorIdTest() {
        Map<Long, Book> books = booksDao.getAllBookByAuthorId(2001L);
        System.out.println(books);
        assertThat(books.isEmpty()).isFalse();
        assertThat(books.get(4L).getName()).isEqualTo("Искатели ветра");
        assertThat(books.size()).isEqualTo(1);
    }

    @Test
    public void deleteByIdTest() {
        booksDao.deleteById(5L);
        Map<Long, Book> books = booksDao.getById(5L);
        assertThat(books.isEmpty()).isTrue();
    }

    @Test
    public void updateBookTest() {
        Book book = new Book(1L, "Очень скучная книга", new Author(1000L), new ArrayList<>());
        booksDao.update(book, 1L);
        Map<Long, Book> books = booksDao.getById(1L);
        assertThat(books.get(1L).getName()).isEqualTo("Очень скучная книга");
        assertThat(books.get(1L).getName()).isNotEqualTo("Война и мир");
    }

    @Test
    public void getAllBooksTest() {
        Map<Long, Book> books = booksDao.getAll();
        System.out.println(books.size());
        assertThat(books.isEmpty()).isEqualTo(Boolean.FALSE);
        assertThat(books.get(1L).getAuthor().getName()).isEqualTo("Лев Толстой");
    }

}