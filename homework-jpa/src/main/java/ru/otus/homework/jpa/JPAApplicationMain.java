package ru.otus.homework.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.h2.tools.Console;

import java.sql.SQLException;

@SpringBootApplication
public class JPAApplicationMain {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(JPAApplicationMain.class, args);
        Console.main(args);
    }
}