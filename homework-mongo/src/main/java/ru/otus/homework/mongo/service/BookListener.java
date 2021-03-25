package ru.otus.homework.mongo.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework.mongo.model.Author;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.model.Comment;
import ru.otus.homework.mongo.repository.AuthorRepository;
import ru.otus.homework.mongo.repository.BookRepository;
import ru.otus.homework.mongo.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookListener extends AbstractMongoEventListener<Book> {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Book> event) {
        //при добавлении книги проверим что такой ещё нет у автора и добавим ему ссылку
        Book book = event.getSource();
        boolean isExists = false;

        Optional<Author> author = authorRepository.findById(book.getAuthor().getId());

        if (author.isPresent()) {
            for (Book book1 : author.get().getBooks()) {
                if (book1.getId().equals(book.getId())) {
                    isExists = true;
                }
            }

            if (!isExists) {
                author.get().getBooks().add(book);
                authorRepository.save(author.get());
            }
        }

        super.onAfterSave(event);
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        //перед удалением книги удалим все комментарии к ней
        String bookID = event.getDocument()
                .get("_id", ObjectId.class).toString();      // BSON Document!
        Optional<Book> book = bookRepository.findById(bookID);

        List<Comment> comments = commentRepository.findAllByBook(book.orElse(null));
        commentRepository.deleteAll(comments);

        super.onBeforeDelete(event);
    }
}
