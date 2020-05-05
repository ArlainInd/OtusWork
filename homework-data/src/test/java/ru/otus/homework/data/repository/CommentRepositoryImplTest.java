package ru.otus.homework.data.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.data.model.Book;
import ru.otus.homework.data.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryImplTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findAllTest() {
        List<Comment> comments = commentRepository.findAll();
        System.out.println(comments);
        assertThat(comments.isEmpty()).isFalse();
        assertThat(comments.size()).isGreaterThan(1);
    }

    @Test
    public void saveTest() {
        Optional<Book> book = bookRepository.findById(1L);
        Comment comment = new Comment("save new comment!", book.orElse(null));
        Comment comIns = commentRepository.save(comment);
        assertThat(comIns.getName()).isEqualTo("save new comment!");
        assertThat(comIns.getBook()).isEqualTo(book.orElse(null));
    }

    @Test
    public void deleteByIdTest() {
        commentRepository.deleteById(1L);
        Optional<Comment> comment = commentRepository.findById(1L);
        assertThat(comment.isPresent()).isFalse();
    }

    @Test
    public void updateTest() {
        commentRepository.update(2L, "New comment");
        Optional<Comment> comment2 = commentRepository.findById(2L);
        assertThat(comment2.orElseGet(null).getName()).isEqualTo("New comment");


    }

    @Test
    public void findAllByBookIdTest() {
        List<Comment> comments = commentRepository.findAllByBookId(5L);
        assertThat(comments.isEmpty()).isFalse();
        assertThat(comments.size()).isGreaterThan(2);
        Optional<Comment> findComment = commentRepository.findById(3L);
        assertThat(findComment.get().getId()).isEqualTo(3L);
        assertThat(findComment.get().getName()).isEqualTo("Неплохая книга");
    }

    @Test
    public void findByIdTest() {
        Optional<Comment> findComment = commentRepository.findById(3L);
        assertThat(findComment.get().getName()).isEqualTo("Неплохая книга");
    }
}