package ru.otus.homework.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
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
