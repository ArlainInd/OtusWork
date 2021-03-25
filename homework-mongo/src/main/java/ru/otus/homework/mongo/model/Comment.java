package ru.otus.homework.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String name;

    @DBRef(lazy = true)
    private Book book;

    public Comment(String name, Book book) {
        this.name = name;
        this.book = book;
    }

    @Override
    public String toString() {
        return "Комментарий: " + " id: " + getId() + " Текст: " + getName();
    }
}
