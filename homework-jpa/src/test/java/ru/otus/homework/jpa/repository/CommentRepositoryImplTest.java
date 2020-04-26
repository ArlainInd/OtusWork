package ru.otus.homework.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.jpa.model.Book;
import ru.otus.homework.jpa.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория CommentRepository")
@DataJpaTest
@Import({CommentRepositoryImpl.class, BookRepositoryImpl.class})
class CommentRepositoryImplTest {

    @Autowired
    private CommentRepositoryImpl commentRepository;

    @Autowired
    private BookRepositoryImpl bookRepository;

    @Test
    void findAllTest() {
        List<Comment> comments = commentRepository.findAll();
        System.out.println(comments);
        assertThat(comments.isEmpty()).isFalse();
        assertThat(comments.size()).isGreaterThan(1);
    }

    @Test
    void saveTest() {
        Optional<Book> book = bookRepository.findBookById(1L);
        Comment comment = new Comment("save new comment!", book.orElse(null), book.orElse(null).getAuthor());
        Comment comIns = commentRepository.save(comment);
        assertThat(comIns.getName()).isEqualTo("save new comment!");
        assertThat(comIns.getBook()).isEqualTo(book.orElse(null));
    }

    @Test
    void deleteByIdTest() {
        Boolean isDel = commentRepository.deleteById(1L);
        assertThat(isDel).isTrue();
        Optional<Comment> comment = commentRepository.findById(1L);
        assertThat(comment.isPresent()).isFalse();
    }

    @Test
    void updateTest() {
        Optional<Comment> comment = commentRepository.findById(2L);
        System.out.println(comment.orElse(null));
        Comment comment1 = comment.orElse(null);
        comment1.setName("New comment");
        System.out.println(comment1);
        Boolean bol = commentRepository.update(comment1);
        assertThat(bol).isTrue();
        Optional<Comment> comment2 = commentRepository.findById(2L);
        assertThat(comment2.orElseGet(null).getName()).isEqualTo("New comment");


    }

    @Test
    void findAllByBookIdTest() {
        List<Comment> comments = commentRepository.findAllByBookId(5L);
        assertThat(comments.isEmpty()).isFalse();
        Optional<Comment> findComment = commentRepository.findById(3L);
        assertThat(comments.contains(findComment.get())).isTrue();
    }

    @Test
    void findByIdTest() {
        Optional<Comment> findComment = commentRepository.findById(3L);
        assertThat(findComment.get().getName()).isEqualTo("Неплохая книга");
    }
}