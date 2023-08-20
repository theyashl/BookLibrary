package com.example.booklibrary.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer rent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonManagedReference
    private Author author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
