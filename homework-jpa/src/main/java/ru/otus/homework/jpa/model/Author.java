package ru.otus.homework.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private Date birth_date;

    @Column(name = "country")
    private String country;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    @Override
    public String toString() {
        return "Автор: " + "id: " + getId() + " Имя: " + getName();
    }

    public Author(String name, Date birth_date, String country, List<Book> books) {
        this.name = name;
        this.birth_date = birth_date;
        this.country = country;
        this.books = books;
    }
}
