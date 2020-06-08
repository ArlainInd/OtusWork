package ru.otus.homework.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {

    @Id
    private String id;

    private String name;

    @Field("birth_date")
    private LocalDate birthDate;

    private String country;

    @DBRef(lazy = true)
    private List<Book> books;

    @Override
    public String toString() {
        return "Автор: " + "id: " + getId() + " Имя: " + getName();
    }

    public Author(String name, LocalDate birthDate, String country, List<Book> books) {
        this.name = name;
        this.birthDate = birthDate;
        this.country = country;
        this.books = books;
    }
}
