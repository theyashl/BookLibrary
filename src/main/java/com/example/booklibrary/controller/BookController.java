package com.example.booklibrary.controller;

import com.example.booklibrary.entity.Author;
import com.example.booklibrary.entity.Book;
import com.example.booklibrary.form.BookForm;
import com.example.booklibrary.repository.AuthorRepository;
import com.example.booklibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public @ResponseBody ResponseEntity addNewBook(@RequestBody BookForm bookForm) {
        Book b = new Book();
        b.setName(bookForm.getName());
        b.setRent(bookForm.getRent());

        Optional<Author> authorResult = authorRepository.findById(bookForm.getAuthorId());

        if (authorResult.isPresent()) b.setAuthor(authorResult.get());
        else return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);

        Book book = bookRepository.save(b);
        return ResponseEntity.created(URI.create(String.format("/books/%d", book.getId()))).build();
    }

    @GetMapping(path = "/{bookId}")
    public @ResponseBody ResponseEntity getBook(@PathVariable Integer bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) return ResponseEntity.ok(optionalBook.get());
        else return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{bookId}")
    public @ResponseBody ResponseEntity updateBook(@PathVariable Integer bookId, @RequestBody BookForm bookForm) {
        Book book;
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) book = optionalBook.get();
        else return ResponseEntity.notFound().build();

        String name = bookForm.getName();
        Integer rent = bookForm.getRent();
        Integer authorId = bookForm.getAuthorId();
        Optional<Author> author = (authorId != null) ? authorRepository.findById(bookForm.getAuthorId())
                : Optional.empty();

        if (name != null) book.setName(name);
        if (rent != null) book.setRent(rent);
        author.ifPresent(book::setAuthor);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
