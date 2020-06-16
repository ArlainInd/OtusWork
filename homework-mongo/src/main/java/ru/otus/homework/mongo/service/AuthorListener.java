package ru.otus.homework.mongo.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework.mongo.model.Author;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.repository.AuthorRepository;
import ru.otus.homework.mongo.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorListener extends AbstractMongoEventListener<Author> {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        //Перед удалением автора удалим все его книги!
        String authorID = event.getDocument()
                .get("_id", ObjectId.class).toString();      // BSON Document!
        Optional<Author> author = authorRepository.findById(authorID);

        List<Book> books = bookRepository.findAllByAuthor(author.orElse(null));
        bookRepository.deleteAll(books);

        super.onBeforeDelete(event);
    }
}
