package com.example.booklibrary.controller;

import com.example.booklibrary.entity.Author;
import com.example.booklibrary.entity.Book;
import com.example.booklibrary.form.BookForm;
import com.example.booklibrary.repository.AuthorRepository;
import com.example.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping(path = "/")
    public @ResponseBody Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping(path = "/")
    public @ResponseBody String addNewBook(@RequestBody BookForm bookForm) {
        Book b = new Book();
        Author author;
        b.setName(bookForm.getName());
        b.setRent(bookForm.getRent());

        Optional<Author> authorResult = authorRepository.findById(bookForm.getAuthorId());
        if (authorResult.isPresent()) {
            author = authorResult.get();
            b.setAuthor(author);
        } else {
            return "Invalid Author Provided";
        }
        bookRepository.save(b);
        return "Saved";
    }
}
