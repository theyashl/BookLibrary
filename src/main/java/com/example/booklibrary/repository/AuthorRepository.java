package com.example.booklibrary.repository;

import com.example.booklibrary.entity.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
}
