package ru.otus.homework.mongo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.mongo.model.Author;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.model.Comment;
import ru.otus.homework.mongo.model.Genre;
import ru.otus.homework.mongo.repository.AuthorRepository;
import ru.otus.homework.mongo.repository.BookRepository;
import ru.otus.homework.mongo.repository.CommentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CascadeDataService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;

    public String bookSave(String name, Author author, Genre genre, List<Comment> comments) {
        //поиск клниги по названию и автору
        Optional<Book> bookFind = bookRepository.findByNameAndAuthor(name, author);
        if (bookFind.isPresent()) {
            if (!comments.isEmpty()) {
                bookFind.get().setComments(comments);
                bookFind.get().setFirstComments(comments.subList(0, 10));
            }
            //если книга есть, то автору список не обновляем, она ужедолжна там быть
            Book book = bookRepository.save(bookFind.get());
            return String.format("Обновили данные по книге с ИД: %s", book.getId());
        }
        else {
            Book bookSave = new Book(name, author, genre);
            if (!comments.isEmpty()) {
                bookSave.setComments(comments);
                bookSave.setFirstComments(comments.subList(0, 10));
            }
            Book book = bookRepository.save(bookSave);
            //для новой книги надо добавить ссылку автору
            author.getBooks().add(book);
            authorRepository.save(author);
            return String.format("Добавили новую книгу с ИД: %s", book.getId());
        }
    }

    public String deleteBook(String name, Author author) {
        Optional<Book> bookFind = bookRepository.findByNameAndAuthor(name, author);
        if (bookFind.isPresent()) {
            bookRepository.delete(bookFind.get());

            List<Comment> comments = commentRepository.findAllByBook(bookFind.get());
            commentRepository.deleteAll(comments);

            Optional<Author> authorFind = authorRepository.findById(bookFind.get().getAuthor().getId());
            if (authorFind.isPresent()) {
                List<Book> books = authorFind.get().getBooks();
                books.remove(bookFind.get());
                authorFind.get().setBooks(books);
                authorRepository.save(authorFind.get());
            }
            return String.format("Удалили книгу с ИД: %s", bookFind.get().getId());
        }
        else {
            return "Не нашли такой книги!";
        }

    }

    public String saveAuthor(String authorName, LocalDate authorDate, String country) {
        Optional<Author> authorFind = authorRepository.findByNameAndBirthDateBetween(authorName, authorDate.minusDays(1), authorDate.plusDays(1));
        if (authorFind.isPresent()) {
            Author authorUpd = authorFind.get();
            authorRepository.save(authorUpd);
            return String.format("Обновили данные по автору с ИД: %s", authorUpd.getId());
        }
        else {
            Author authorNew = authorRepository.save(new Author(authorName, authorDate, country, new ArrayList<>()));
            return String.format("Добавили нового автора с ИД: %s", authorNew.getId());
        }
    }

    public  String deleteAuthor(String authorName, LocalDate authorDate) {
        Optional<Author> authorFind = authorRepository.findByNameAndBirthDateBetween(authorName, authorDate.minusDays(1), authorDate.plusDays(1));
        if (authorFind.isPresent()) {
            authorRepository.delete(authorFind.get());
            List <Book> books = bookRepository.findAllByAuthor(authorFind.get());
            for (Book book : books) {
                List<Comment> comments = commentRepository.findAllByBook(book);
                commentRepository.deleteAll(comments);
            }
            bookRepository.deleteAll(books);

            return String.format("Удалили автора с ИД: %s", authorFind.get().getId());
        }
        else {
            return "Автора с такими параметрами нет в каталоге";
        }
    }

    public String saveComment(String commentOld, String commentNew, String bookName, String authorName, LocalDate authorDate) {
        boolean isUpd = false;
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween(authorName, authorDate.minusDays(1), authorDate.plusDays(1));
        if (author.isPresent()) {
            List<Book> books = author.get().getBooks();
            for (Book book : books) {
                if (book.getName().equals(bookName)) {
                    Optional<Comment> commentFind;
                    if (commentOld == null) {
                        commentFind = commentRepository.findByNameAndBook(commentNew, book);
                        if (!commentFind.isPresent()) {
                            Comment comment = new Comment(commentNew, book);
                            commentRepository.save(comment);
                            if (book.getComments() != null && book.getComments().size() > 0) {book.getComments().add(comment);}
                            else {
                                List<Comment> comments = new ArrayList<>();
                                comments.add(comment);
                                book.setComments(comments);
                            }

                            if (book.getFirstComments() != null && book.getFirstComments().size() > 0) {
                                int cnt = book.getFirstComments().size();
                                if (cnt <= 10) {
                                    book.getFirstComments().add(comment);
                                }
                            }
                            else {
                                List<Comment> comments = new ArrayList<>();
                                comments.add(comment);
                                book.setFirstComments(comments);
                            }
                            bookRepository.save(book);
                            isUpd = true;
                        }
                    }
                    else {
                        commentFind = commentRepository.findByNameAndBook(commentOld, book);
                        if (commentFind.isPresent()) {
                            Comment comment = commentFind.get();
                            comment.setName(commentNew);
                            commentRepository.save(comment);

                            if (book.getComments() != null && book.getComments().size() > 0) {
                                List<Comment> comments = new ArrayList<>();
                                for (Comment commentPrev : book.getComments() ) {
                                    if (!commentPrev.getName().equals(commentOld)) {
                                        comments.add(commentPrev);
                                    }
                                }
                                comments.add(comment);
                                book.setComments(comments);
                            }

                            if (book.getFirstComments() != null && book.getFirstComments().size() > 0) {
                                List<Comment> comments2 = new ArrayList<>();
                                for (Comment commentPrev2 : book.getFirstComments() ) {
                                    if (!commentPrev2.getName().equals(commentOld)) {
                                        comments2.add(commentPrev2);
                                    }
                                }
                                int cnt = comments2.size();
                                if (cnt <= 10) {
                                    comments2.add(comment);
                                }
                                book.setFirstComments(comments2);
                            }
                            bookRepository.save(book);
                            isUpd = true;
                        }
                    }
                }
            }
        }
        if (isUpd) {
            return "Успешно добавили\\изменили комментарий";
        }
        else {
            return "Не смогли добавить комментарий";
        }
    }

    public String deleteComment(String comment, String bookName, String authorName, LocalDate authorDate) {
        boolean isDel = false;
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween(authorName, authorDate.minusDays(1), authorDate.plusDays(1));
        if (author.isPresent()) {
            List<Book> books = author.get().getBooks();
            for (Book book : books) {
                if (book.getName().equals(bookName)) {
                    Optional<Comment> commentFind = commentRepository.findByNameAndBook(comment, book);
                    if (commentFind.isPresent()) {
                        commentRepository.delete(commentFind.get());
                        if (book.getComments() != null && book.getComments().size() > 0) {
                            List<Comment> comments = new ArrayList<>();
                            for (Comment commentPrev : book.getComments() ) {
                                if (!commentPrev.getName().equals(commentFind.get().getName())) {
                                    comments.add(commentPrev);
                                }
                            }
                            if (comments.size() > 0) {book.setComments(comments);}
                        }
                        if (book.getFirstComments() != null && book.getFirstComments().size() > 0) {
                            List<Comment> comments = new ArrayList<>();
                            for (Comment commentPrev : book.getFirstComments() ) {
                                if (!commentPrev.getName().equals(commentFind.get().getName())) {
                                    comments.add(commentPrev);
                                }
                            }
                            if (comments.size() > 0) { book.setFirstComments(comments);}
                        }
                        bookRepository.save(book);
                        isDel = true;
                    }
                }
            }
        }
        if (isDel) {
            return "Успешно удалили комментарий";
        }
        else {
            return "Не смогли удалить комментарий";
        }
    }

    public String deleteAllComment(String bookName, String authorName, LocalDate authorDate) {
        boolean isDel = false;
        Optional<Author> author = authorRepository.findByNameAndBirthDateBetween(authorName, authorDate.minusDays(1), authorDate.plusDays(1));
        if (author.isPresent()) {
            List<Book> books = author.get().getBooks();
            for (Book book : books) {
                if (book.getName().equals(bookName)) {
                    commentRepository.deleteByBook(book);
                    if ((book.getComments() != null) && (book.getComments().size() > 0)) {book.getComments().clear();}
                    if ((book.getFirstComments() != null) && (book.getFirstComments().size() > 0)) {
                        book.getFirstComments().clear();
                    }
                    bookRepository.save(book);
                    isDel = true;
                }
            }
        }
        if (isDel) {
            return String.format("Успешно удалили все комментарий к книге %s", bookName);
        }
        else {
            return String.format("Не смогли удалить комментарий к книге %s", bookName);
        }
    }
}
