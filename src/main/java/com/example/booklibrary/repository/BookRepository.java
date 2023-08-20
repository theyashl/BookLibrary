package com.example.booklibrary.repository;

import com.example.booklibrary.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
