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
import ru.otus.homework.jpa.repository.GenreRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ShellComponent
@RequiredArgsConstructor
public class ShellService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

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
            System.out.println("Комментраий: " + comment.getName() + " к книге: " + comment.getBook().getName());
        }
        return String.format("Итого найдено комментариев: %s", comments.size());
    }

    @ShellMethod(value = "Просмотреть автора", key = {"fa", "findauthor"})
    public String findAuthorById(Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        return author.toString();
    }

    @ShellMethod(value = "Удалить автора", key = {"da", "delauthor"})
    public String deleteAuthorById(Long authorId) {
        Boolean bool = authorRepository.deleteById(authorId);
        if (bool) {
            return String.format("Удалили автора с ИД: %s", authorId);
        }
        else {
            return String.format("Не смогли удалить автора с ИД: %s", authorId);
        }
    }

    @ShellMethod(value = "Изменить автора", key = {"ua", "updauthor"})
    public String UpdateAuthorNameById(Long authorId, String name, String date, String country) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date birthdate = formatter.parse(date);
        Author author = new Author(authorId, name, birthdate, country, new ArrayList<>());
        Boolean bool = authorRepository.update(author);
        if (bool) {
            return String.format("Обновили автора с ИД: %s", authorId);
        }
        else {
            return String.format("Не смогли обновить автора с ИД: %s", authorId);
        }
    }

    @ShellMethod(value = "Просмотреть все комментарии в каталоге", key = {"fac", "findallcomment"})
    public String findAllComment() {
        List<Comment> comments = commentRepository.findAll();
        for (Iterator<Comment> iter = comments.iterator(); iter.hasNext();) {
            Comment comment = iter.next();
            System.out.println(comment.toString());
        }
        return String.format("Итого найдено комментариев в каталоге: %s", comments.size());
    }

    @ShellMethod(value = "Добавить комментарий к книге", key = {"ic", "inscomment"})
    public String insertComment(String comment, Long bookId, Long authorId) {
        Optional<Book> book = bookRepository.findBookById(bookId);
        if (!book.isPresent()) {
            return String.format("Не смогли найти книгу с ИД: %s", bookId);
        }
        Comment commentNew = new Comment(comment, book.get());
        Comment commentIns = commentRepository.save(commentNew);
        return String.format("Успешно добавили комментарий");
    }

    @ShellMethod(value = "Удалить комментарий", key = {"dc", "delcomment"})
    public String deleteCommentById(Long commentId) {
        Boolean bool = commentRepository.deleteById(commentId);
        if (bool) {
            return String.format("Удалили комментарий с ИД: %s", commentId);
        }
        else {
            return String.format("Не смогли удалить комментарий с ИД: %s", commentId);
        }
    }

    @ShellMethod(value = "Изменить комментарий", key = {"uc", "updcomment"})
    public String updateCommentById(Long commentId, String name) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (!findComment.isPresent()) {
            return String.format("Не смогли найти комментарий с ИД: %s", commentId);
        }
        Comment comment = new Comment(commentId, name, findComment.get().getBook());
        Boolean bool = commentRepository.update(comment);
        if (bool) {
            return String.format("Обновили комментарий с ИД: %s", commentId);
        }
        else {
            return String.format("Не смогли обновить комментарий с ИД: %s", commentId);
        }
    }
}
