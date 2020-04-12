package ru.otus.homework.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework.jdbc.dao.BooksDao;
import ru.otus.homework.jdbc.domain.Author;
import ru.otus.homework.jdbc.domain.Book;
import ru.otus.homework.jdbc.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JdbcMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcMainApplication.class, args);
    }
}
