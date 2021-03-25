package ru.otus.homework.mongo.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework.mongo.model.Book;
import ru.otus.homework.mongo.model.Comment;
import ru.otus.homework.mongo.repository.BookRepository;
import ru.otus.homework.mongo.repository.CommentRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentListener extends AbstractMongoEventListener<Comment> {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Comment> event) {
        //при добавлении комментария надо данные по нему добавить в саму книгу
        Comment comment = event.getSource();
        Book book = comment.getBook();
        boolean isExists = false;
        boolean isNeedUpd = false;
        Optional<Book> bookFind = bookRepository.findById(book.getId());
        if (bookFind.isPresent()) {
            Book bookSave = bookFind.get();
            if (bookFind.get().getComments() != null && bookFind.get().getComments().size() > 0) {
                for (Comment commentFind : bookFind.get().getComments()) {
                    if (commentFind.getId().equals(comment.getId())) {
                        isExists = true;
                    }
                }

                if (!isExists) {
                    bookSave.getComments().add(comment);
                    isNeedUpd = true;
                }
            } else {
                List<Comment> comments = new ArrayList<>();
                comments.add(comment);
                bookSave.setComments(comments);
            }

            if (bookFind.get().getFirstComments() != null && bookFind.get().getFirstComments().size() > 0) {
                for (Comment commentFind : bookFind.get().getFirstComments()) {
                    if (commentFind.getId().equals(comment.getId())) {
                        isExists = true;
                    }
                }

                if (!isExists) {
                    bookSave.getFirstComments().add(comment);
                    isNeedUpd = true;
                }
            } else {
                List<Comment> comments = new ArrayList<>();
                comments.add(comment);
                bookSave.setFirstComments(comments);
            }

            if (isNeedUpd) {
                bookRepository.save(bookSave);
            }
        }

        super.onAfterSave(event);
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Comment> event) {
        //При удалении комментария надо удалить и ссылки на него в книге
        String commentID = event.getDocument()
                .get("_id", ObjectId.class).toString();      // BSON Document!
        Optional<Comment> comment = commentRepository.findById(commentID);

        if (comment.isPresent()) {
            Optional<Book> book = bookRepository.findById(comment.get().getBook().getId());
            if (book.isPresent()) {
                //в книге найдем эти комментарии в двух местах - "комментарии" и "10 первых комментариев"
                if (book.get().getComments() != null && book.get().getComments().size() > 0) {
                    List<Comment> comments = book.get().getComments();

                    Iterator<Comment> iter = comments.iterator();
                    while (iter.hasNext()) {
                        Comment commentPrev = iter.next();
                        if (commentPrev.getId().equals(comment.get().getId())) {
                            iter.remove();
                        }
                    }
                }
                if (book.get().getFirstComments() != null && book.get().getFirstComments().size() > 0) {
                    List<Comment> comments = book.get().getFirstComments();

                    Iterator<Comment> iter = comments.iterator();
                    while (iter.hasNext()) {
                        Comment commentPrev = iter.next();
                        if (commentPrev.getId().equals(comment.get().getId())) {
                            iter.remove();
                        }
                    }
                }
                bookRepository.save(book.get());
            }
        }

        super.onBeforeDelete(event);
    }
}
