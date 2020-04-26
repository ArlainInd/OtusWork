package ru.otus.homework.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.jpa.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование репозитория GenreRepository")
@DataJpaTest
@Import(GenreRepositoryImpl.class)
class GenreRepositoryImplTest {

    @Autowired
    GenreRepositoryImpl genreRepository;

    @Test
    void saveTest() {
        Genre genre = new Genre("НФ");
        Genre genreNew = genreRepository.save(genre);
        assertThat(genreNew.getName()).isEqualTo("НФ");
        assertThat(genreNew.getId()).isGreaterThan(0L);
    }

    @Test
    void updateTest() {
        Optional<Genre> genre = genreRepository.findById(3L);
        genre.orElse(null).setName("Ужастик");
        Boolean bol = genreRepository.update(genre.orElse(null));
        assertThat(bol).isTrue();
        Optional<Genre> genreUpd = genreRepository.findById(3L);
        assertThat(genreUpd.orElse(null).getName()).isEqualTo("Ужастик");
    }

    @Test
    void deleteByIdTest() {
        Boolean bol = genreRepository.deleteById(2L);
        assertThat(bol).isTrue();
        Optional<Genre> genre = genreRepository.findById(2L);
        assertThat(genre).isEmpty();
    }

    @Test
    void findAllTest() {
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres.size()).isGreaterThan(1);
        assertThat(genres.isEmpty()).isFalse();
    }

    @Test
    void findByIdTest() {
        Optional<Genre> genre = genreRepository.findById(1L);
        assertThat(genre.orElse(null).getName()).isEqualTo("Роман");
    }
}