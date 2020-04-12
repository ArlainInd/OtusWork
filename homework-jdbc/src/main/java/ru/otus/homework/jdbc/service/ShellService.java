package ru.otus.homework.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.jdbc.dao.AuthorsDao;
import ru.otus.homework.jdbc.dao.BooksDao;
import ru.otus.homework.jdbc.dao.GenresDao;
import ru.otus.homework.jdbc.domain.Author;
import ru.otus.homework.jdbc.domain.Book;
import ru.otus.homework.jdbc.domain.Genre;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ShellComponent
@RequiredArgsConstructor
public class ShellService {

    private Book book;
    private final BooksDao booksDao;
    private final GenresDao genresDao;
    private final AuthorsDao authorsDao;

    @ShellMethod(value = "Поиск книг по автору", key = {"fa", "findByAuthor"})
    public String findBookByAuthorId(Long id) {
        Map<Long, Book> map = booksDao.getAllBookByAuthorId(id);
        map.forEach((k,v)->{
            System.out.println("ИД: " + k + "; Книга: " + v.getName() + "; Автор: " + v.getAuthor().getName());
        });
        return String.format("Итого найдено книг: %s", map.size());
    }

    @ShellMethod(value = "Поиск всех книг каталога", key = {"fab", "findAllBook", "allBook"})
    public String findAllBook() {
        Map<Long, Book> map = booksDao.getAll();
        map.forEach((k,v) -> {
            System.out.println("ИД: " + k + "; Автор: " + v.getAuthor().getName() + "; Книга: " + v.getName());
        });
        return String.format("Итого найдено книг: %s", map.size());
    }

    @ShellMethod(value = "Удалить книгу из каталога", key = {"delb", "deleteBook"})
    public String deleteBookById(Long id) {
        booksDao.deleteById(id);
        return String.format("Успешно удалили книгу из каталога с ИД = %s", id);
    }

    @ShellMethod(value = "Изменить книгу из каталога", key = {"ub", "updateBook"})
    public String updateBookByParam(Long bookId, String name, Long authorId) {
        Book book = new Book(bookId, name, new Author(authorId), new ArrayList<>());
        booksDao.update(book, bookId);
        return String.format("Успешно обновили книгу из каталога с ИД = %s", bookId);
    }

    @ShellMethod(value = "Добавить книгу в каталог", key = {"ib", "insBook"})
    public String insertBook(Long bookId, String name, Long authorId, Long genreId) {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(genreId));
        Book book = new Book(bookId, name, new Author(authorId), genres);
        booksDao.insert(book);
        return String.format("Успешно добавили книгу в каталог с ИД = %s", bookId);
    }

    @ShellMethod(value = "Добавить жанр книги", key = {"ig", "insGenre"})
    public String insertGenre(Long id, String name) {
        Genre genre = new Genre(id, name);
        genresDao.insert(genre);
        return String.format("Успешно добавили жанр книг с ИД = %s", id);
    }

    @ShellMethod(value = "Удалить жанр книги", key = {"dg", "delGenre"})
    public String deleteGenre(Long id) {
        Boolean res = genresDao.deleteById(id);
        if (res) {return String.format("Успешно удалили жанр книг с ИД = %s", id);}
        else {return String.format("Возникла ошибка при удалении жанра! Убедитесь что нет книг в каталоге с жанром = %s", id);}
    }

    @ShellMethod(value = "Обновить жанр книги", key = {"ug", "updGenre"})
    public String updateGenre(Long id, String name) {
        genresDao.update(id, name);
        return String.format("Успешно обновили жанр книг с ИД = %s", id);
    }

    @ShellMethod(value = "Просмотр жанров книг", key = {"fag", "findAllGenre"})
    public String findAllGenre() {
        List<Genre> genreList = genresDao.getAll();
        genreList.forEach(genre -> System.out.println("ИД: " + genre.getGenreId() + "; Название: " + genre.getName()));
        return String.format("ВСего жанров в базе: %s", genreList.size());
    }

    @ShellMethod(value = "Добавить автора книг", key = {"insa", "insAuthor"})
    public String insertAuthor(Long authorId, String name, String birthdate, String country) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = formatter.parse(birthdate);
        Author author = new Author(authorId, name, date, country, new ArrayList<>());
        authorsDao.insert(author);
        return String.format("Добавили автора книг с ИД: %s", authorId);
    }

    @ShellMethod(value = "Вывести всех авторов книг", key = {"faa", "findAllAuthor"})
    public String findAllAuthor() {
        Map<Long, Author> authors = authorsDao.getAll();
        authors.forEach((k,v) -> {
            System.out.println("ИД: " + k + "; Автор: " + v.getName() + "; Книга: " + v.getBooks());
        });
        return String.format("Всего авторов книг: %s", authors.size());
    }

    @ShellMethod(value = "Вывести всех авторов книг", key = {"da", "delAuthor"})
    public String deleteAuthor(Long authorId) {
        authorsDao.deleteById(authorId);
        Map<Long, Author> findAuthor = authorsDao.getById(authorId);
        if (findAuthor.isEmpty()) {
            return String.format("Удалили авторов книг с ИД: %s", authorId);
        }
        else {
            return "";
        }
    }

    @ShellMethod(value = "Вывести всех авторов книг", key = {"ua", "updAuthor"})
    public String updateAuthor(Long authorId, String name, String birthdate, String country) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = formatter.parse(birthdate);
        Author author = new Author(authorId, name, date, country, new ArrayList<>());
        authorsDao.update(author, authorId);
        return String.format("Обновили автора книг с ИД: %s", authorId);
    }

}

