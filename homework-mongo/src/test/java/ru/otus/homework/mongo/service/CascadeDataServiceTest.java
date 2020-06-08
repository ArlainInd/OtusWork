package ru.otus.homework.mongo.service;

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
import ru.otus.homework.mongo.repository.AuthorRepository;
import ru.otus.homework.mongo.repository.BookRepository;
import ru.otus.homework.mongo.repository.CommentRepository;
import ru.otus.homework.mongo.repository.GenreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class CascadeDataServiceTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    private CascadeDataService dataService;

    private String authorID;
    private String genreID;
    private String bookID;
    private String commentID;

    @BeforeEach
    void setup() {
        commentRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();

        dataService = new CascadeDataService(bookRepository, authorRepository, commentRepository);

        LocalDate date = LocalDate.of(1936, 11, 12);
        Author author = new Author("Роберт Сальваторе", date, "США", new ArrayList<>());
        Author authorSave = authorRepository.save(author);
        authorID = authorSave.getId();

        LocalDate date2 = LocalDate.of(1921, 11, 12);
        Author author2 = new Author("Роберт Джордан", date2, "США", new ArrayList<>());
        authorRepository.save(author2);

        Genre genre = new Genre("Роман");
        Genre genreSave = genreRepository.save(genre);
        genreID = genreSave.getId();

        dataService.bookSave("Темный эльф", authorSave, genreSave, new ArrayList<>());
        dataService.bookSave("Беззвездная ночь", authorSave, genreSave, new ArrayList<>());

        Optional<Book> b1 = bookRepository.findByNameAndAuthor("Темный эльф", authorSave);
        bookID = b1.get().getId();

        Comment comment = new Comment("Тут могла быть ваша реклама", b1.get());
        Comment commentNew = commentRepository.save(comment);
        commentID = commentNew.getId();
    }

    @Test
    void bookSave() {
        Optional<Author> author = authorRepository.findById(authorID);
        Optional<Genre> genre = genreRepository.findById(genreID);

        dataService.bookSave("Долина ледяного ветра", author.get(), genre.get(), new ArrayList<>());

        Optional<Book> book = bookRepository.findByNameAndAuthor("Долина ледяного ветра", author.get());

        assertThat(book.isPresent()).isTrue();
        assertThat(book.get().getName()).isEqualTo("Долина ледяного ветра");
    }

    @Test
    void deleteBook() {
        Optional<Author> author = authorRepository.findById(authorID);
        dataService.deleteBook("Беззвездная ночь", author.get());

        Optional<Book> book = bookRepository.findByNameAndAuthor("Беззвездная ночь", author.get());
        assertThat(book.isPresent()).isFalse();
    }

    @Test
    void saveAuthor() {
        LocalDate date = LocalDate.of(1991, 5, 28);
        dataService.saveAuthor("Воронов Василий", date, "Россия");

        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween("Воронов Василий", date.minusDays(1), date.plusDays(1));
        assertThat(author.isPresent()).isTrue();
        assertThat(author.get().getName()).isEqualTo("Воронов Василий");
    }

    @Test
    void deleteAuthor() {
        LocalDate date = LocalDate.of(1921, 11, 12);
        dataService.deleteAuthor("Роберт Джордан", date);

        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween("Роберт Джордан", date.minusDays(1), date.plusDays(1));
        assertThat(author.isPresent()).isFalse();
    }

    @Test
    void saveComment() {
        Optional<Comment> commentOld = commentRepository.findById(commentID);

        LocalDate date = LocalDate.of(1936, 11, 12);
        dataService.saveComment("Тут могла быть ваша реклама", "Тут нет рекламы", "Темный эльф", "Роберт Сальваторе", date);
        Optional<Comment> commentNew = commentRepository.findById(commentID);

        assertThat(commentOld.get().getName()).isNotEqualTo(commentNew.get().getName());
    }

    @Test
    void deleteComment() {
        LocalDate date = LocalDate.of(1936, 11, 12);
        Optional<Author> author = authorRepository.findById(authorID);
        Optional<Genre> genre = genreRepository.findById(genreID);

        dataService.bookSave("Просто ещё одна книга", author.get(), genre.get(), new ArrayList<>());

        dataService.saveComment(null, "Комментарий","Просто ещё одна книга", "Роберт Сальваторе", date);
        dataService.saveComment(null, "Комментарий2","Просто ещё одна книга", "Роберт Сальваторе", date);

        Optional<Book> book = bookRepository.findByNameAndAuthor("Просто ещё одна книга", author.get());

        Optional<Comment> comment = commentRepository.findByNameAndBook("Комментарий", book.get());

        dataService.deleteComment("Комментарий", "Просто ещё одна книга", "Роберт Сальваторе", date);

        Optional<Comment> commentDel = commentRepository.findByNameAndBook("Комментарий", book.get());

        assertThat(commentDel).isEmpty();
        assertThat(comment).isNotEqualTo(commentDel);
    }

    @Test
    void deleteAllComment() {
        LocalDate date = LocalDate.of(1936, 11, 12);
        Optional<Author> author = authorRepository.findById(authorID);
        Optional<Genre> genre = genreRepository.findById(genreID);

        dataService.bookSave("Просто ещё одна книга2", author.get(), genre.get(), new ArrayList<>());

        Optional<Book> book = bookRepository.findByNameAndAuthor("Просто ещё одна книга2", author.get());
        dataService.saveComment(null, "Комментарий","Просто ещё одна книга2", "Роберт Сальваторе", date);
        dataService.saveComment(null, "Комментарий2","Просто ещё одна книга2", "Роберт Сальваторе", date);
        dataService.saveComment(null, "Комментарий3","Просто ещё одна книга2", "Роберт Сальваторе", date);

        List<Comment> comments = commentRepository.findAllByBook(book.get());

        dataService.deleteAllComment("Просто ещё одна книга2", "Роберт Сальваторе", date);
        List<Comment> commentsDel = commentRepository.findAllByBook(book.get());

        assertThat(comments.size()).isNotEqualTo(commentsDel.size());
        assertThat(commentsDel.size()).isEqualTo(0);

    }
}