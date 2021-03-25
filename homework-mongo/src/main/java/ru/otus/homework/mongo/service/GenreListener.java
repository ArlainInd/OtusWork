package ru.otus.homework.mongo.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.model.Genre;
import ru.otus.homework.mongo.repository.BookRepository;
import ru.otus.homework.mongo.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreListener extends AbstractMongoEventListener<Genre> {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        //перед удалением жанра удалим все книги с таким жанром
        String genreID = event.getDocument()
                .get("_id", ObjectId.class).toString();      // BSON Document!

        Optional<Genre> genre = genreRepository.findById(genreID);

        List<Book> books = bookRepository.findAllByGenre(genre.orElse(null));
        bookRepository.deleteAll(books);

        super.onBeforeDelete(event);
    }
}
