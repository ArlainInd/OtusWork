package ru.otus.homework.mongo.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.mongo.model.Author;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.model.Comment;
import ru.otus.homework.mongo.model.Genre;
import ru.otus.homework.mongo.repository.AuthorRepository;
import ru.otus.homework.mongo.repository.BookRepository;
import ru.otus.homework.mongo.repository.CommentRepository;
import ru.otus.homework.mongo.repository.GenreRepository;
import ru.otus.homework.mongo.service.CascadeDataService;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@ShellComponent
@RequiredArgsConstructor
public class ShellService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CascadeDataService dataService;

    @ShellMethod(value = "Поиск всех книг каталога", key = {"fab", "findAllBook", "allBook"})
    public String findAllBook() {
        List<Book> books = bookRepository.findAll();
        for (Iterator<Book> bookIterator = books.iterator(); bookIterator.hasNext();) {
            Book book = bookIterator.next();
            System.out.println(book.getName());
        }
        return String.format("Итого найдено книг: %s", books.size());
    }

    @ShellMethod(value = "Добавить автора", key = {"ca", "createauthor"})
    public String createAuthor(String name, String authorDate, String country) throws ParseException {
        LocalDate localDate0 = LocalDate.parse(authorDate);
        LocalDate localDate1 = LocalDate.parse(authorDate).minusDays(1);
        LocalDate localDate2 = LocalDate.parse(authorDate).plusDays(1);
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween(name, localDate1, localDate2);
        if (author.isPresent()) {
            return "Такой автор уже есть в каталоге";
        }
        else  {
            return dataService.saveAuthor(name, localDate0, country);
        }
    }

    @ShellMethod(value = "Просмотреть все комментарии к книге", key = {"ac", "allcomment"})
    public String findAllCommentByBookId(String bookName, String authorName, String authorDate) {
        LocalDate localDate = LocalDate.parse(authorDate);
        LocalDate localDate1 = LocalDate.parse(authorDate).minusDays(1);
        LocalDate localDate2 = LocalDate.parse(authorDate).plusDays(1);
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween(authorName, localDate1, localDate2);
        Optional<Book> book = bookRepository.findByNameAndAuthor(bookName, author.get());
        for (Comment comment : book.get().getComments()) {
            System.out.println("Комментраий: " + comment.getName() + " к книге: " + comment.getBook().getName());
        }
        return String.format("Итого найдено комментариев: %s", book.get().getComments().size());
    }

    @ShellMethod(value = "Поиск автора", key = {"fa", "findauthor"})
    public String findAuthor(String authorName, String authorDate) {
        LocalDate localDate1 = LocalDate.parse(authorDate).minusDays(1);
        LocalDate localDate2 = LocalDate.parse(authorDate).plusDays(1);
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween(authorName, localDate1, localDate2);
        if (author.isPresent()) {
            return author.get().toString();
        }
        else {
            return "Автора с такими параметрами нет в каталоге";
        }
    }

    @ShellMethod(value = "Удалить автора", key = {"da", "delauthor"})
    public String deleteAuthor(String authorName, String authorDate) {
        LocalDate localDate0 = LocalDate.parse(authorDate);
        LocalDate localDate1 = LocalDate.parse(authorDate).minusDays(1);
        LocalDate localDate2 = LocalDate.parse(authorDate).plusDays(1);
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween(authorName, localDate1, localDate2);
        if (author.isPresent()) {
            return dataService.deleteAuthor(authorName, localDate0);
        }
        else {
            return "Автора с такими параметрами нет в каталоге";
        }

    }

    @ShellMethod(value = "Изменить автора", key = {"ua", "updauthor"})
    public String UpdateAuthorNameById(String authorName, String authorDate, String country) throws ParseException {
        LocalDate localDate0 = LocalDate.parse(authorDate);
        LocalDate localDate1 = LocalDate.parse(authorDate).minusDays(1);
        LocalDate localDate2 = LocalDate.parse(authorDate).plusDays(1);
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween(authorName, localDate1, localDate2);
        if (author.isPresent()) {
            return dataService.saveAuthor(authorName, localDate0, country);
        }
        else {
            return "Автора с такими параметрами нет в каталоге";
        }
    }

    @ShellMethod(value = "Просмотреть все комментарии в каталоге", key = {"fac", "findallcomment"})
    public String findAllComment() {
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            System.out.println(comment.toString());
        }
        return String.format("Итого найдено комментариев в каталоге: %s", comments.size());
    }

    @ShellMethod(value = "Добавить комментарий к книге", key = {"ic", "inscomment"})
    public String insertComment(String comment, String bookName, String authorName, String authorDate) {
        LocalDate localDate = LocalDate.parse(authorDate);
        return dataService.saveComment(null, comment, bookName, authorName, localDate);
    }

    @ShellMethod(value = "Удалить комментарий к книге", key = {"dc", "delcomment"})
    public String deleteComment(String comment, String bookName, String authorName, String authorDate) {
        LocalDate localDate = LocalDate.parse(authorDate);
        return dataService.deleteComment(comment, bookName, authorName, localDate);
    }

    @ShellMethod(value = "Удалить все комментарий к книге", key = {"dac", "delallcomment"})
    public String deleteAllCommentByBook(String bookName, String authorName, String authorDate) {
        LocalDate localDate = LocalDate.parse(authorDate);
        return dataService.deleteAllComment(bookName, authorName, localDate);
    }

    @ShellMethod(value = "Изменить комментарий", key = {"uc", "updcomment"})
    public String updateCommentById(String commentOld, String commentNew, String bookName, String authorName, String authorDate) {
        LocalDate localDate = LocalDate.parse(authorDate);
        return dataService.saveComment(commentOld, commentNew, bookName, authorName, localDate);
    }

    @ShellMethod(value = "Добавить книгу автора в каталог", key = {"ab", "addbook"})
    public String insertBook(String bookName, String genre, String authorName, String authorDate) {
        LocalDate localDate1 = LocalDate.parse(authorDate).minusDays(1);
        LocalDate localDate2 = LocalDate.parse(authorDate).plusDays(1);
        Optional<Author> authorFind = authorRepository.findByNameAndBirthDateBetween(authorName, localDate1, localDate2);
        Optional<Genre> genreFind = genreRepository.findByName(genre);
        if (authorFind.isPresent() && genreFind.isPresent()) {
            try {
                return dataService.bookSave(bookName, authorFind.get(), genreFind.get(), new ArrayList<>());
            } catch (Exception e) {
                return String.format("Возникла ошибка при добавлении книги: %s", e.getMessage());
            }
        }
        else {
            return "Неправильные данные для добавления книги";
        }
    }

    @ShellMethod(value = "Удалить книгу автора из каталога", key = {"db", "delbook"})
    public String deleteBook(String bookName, String authorName, String authorDate) {
        LocalDate localDate1 = LocalDate.parse(authorDate).minusDays(1);
        LocalDate localDate2 = LocalDate.parse(authorDate).plusDays(1);
        Optional<Author> authorFind = authorRepository.findByNameAndBirthDateBetween(authorName, localDate1, localDate2);
        if (authorFind.isPresent()) {
            try {
                String isDel = dataService.deleteBook(bookName, authorFind.get());
                return isDel;
            } catch (Exception e) {
                return String.format("Возникла ошибка при удалении книги: %s", e.getMessage());
            }
        }
        else {
            return "Неправильно указан автор книги";
        }
    }
}
