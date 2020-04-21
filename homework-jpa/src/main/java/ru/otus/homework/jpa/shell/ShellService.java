package ru.otus.homework.jpa.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import ru.otus.homework.jpa.model.Author;
import ru.otus.homework.jpa.model.Book;
import ru.otus.homework.jpa.model.Comment;
import ru.otus.homework.jpa.repository.AuthorRepository;
import ru.otus.homework.jpa.repository.BookRepository;
import ru.otus.homework.jpa.repository.CommentRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ShellComponent
@RequiredArgsConstructor
public class ShellService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;

    @ShellMethod(value = "Поиск всех книг каталога", key = {"fab", "findAllBook", "allBook"})
    public String findAllBook() {
        List<Book> books = bookRepository.findAll();
        for (Iterator<Book> bookIterator = books.iterator(); bookIterator.hasNext();) {
            Book book = bookIterator.next();
            System.out.println(book.getName());
        }
        return String.format("Итого найдено книг: %s", books.size());
    }

    @ShellMethod(value = "Добавить комментарий автора к книге", key = {"cc", "createcomment"})
    public String createComment(Long bookId, Long authorId, String comment) {
        Optional<Book> book = bookRepository.findBookById(bookId);
        Optional<Author> author = authorRepository.findById(authorId);
        Comment commentNew = new Comment(comment, book.get(), author.get());
        //try {
        commentRepository.SaveComment(commentNew);
        return String.format("Успешно добавили комментарий: %s", comment);
        /*}
        catch (Exception e) {
            return "Не смогли добавить комментарий. Возникла ошибка - " + e.getMessage();
        }*/
    }

    @ShellMethod(value = "Добавить автра", key = {"ca", "createauthor"})
    public String createAuthor(String name, String date, String country) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date birthdate = formatter.parse(date);
        List<Book> books = new ArrayList<>();
        Author authorNew = new Author(name, birthdate, country, books);
        Author author = authorRepository.saveAuthor(authorNew);
        return author.toString();
    }

    @ShellMethod(value = "Просмотреть все комментарии к книге", key = {"ac", "allcomment"})
    public String findAllCommentByBookId(Long bookId) {
        List<Comment> comments = commentRepository.findAllByBookId(bookId);
        for (Iterator<Comment> commentIterator = comments.iterator(); commentIterator.hasNext();) {
            Comment comment = commentIterator.next();
            System.out.println("Комментраий: " + comment.getName() + " к книге: " + comment.getBook().getName() + " от Автора:" + comment.getAuthor().getName());
        }
        return String.format("Итого найдено комментариев: %s", comments.size());
    }

    @ShellMethod(value = "Просмотреть автра", key = {"fa", "findauthor"})
    public String findAuthorById(Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        return author.toString();
    }

    @ShellMethod(value = "Удалить автра", key = {"da", "delauthor"})
    public String deleteAuthorById(Long authorId) {
        Boolean bool = authorRepository.deleteById(authorId);
        if (bool) {
            return "Удалили автора";
        }
        else {
            return "Несмогли удалить автора";
        }
    }

    @ShellMethod(value = "Изменить имя автра", key = {"ua", "updauthor"})
    public String UpdateAuthorNameById(Long authorId, String name) {
        Boolean bool = authorRepository.updateNameById(authorId, name);
        if (bool) {
            return "Обновили автора";
        }
        else {
            return "Несмогли обновить автора";
        }
    }
}
