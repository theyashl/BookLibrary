package com.example.booklibrary.controller;

import com.example.booklibrary.entity.Author;
import com.example.booklibrary.form.AuthorForm;
import com.example.booklibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping(path = "/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping(path = "/")
    public @ResponseBody Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @PostMapping(path = "/")
    public @ResponseBody ResponseEntity<HttpStatus> addNewAuthor(@RequestBody AuthorForm authorForm) {
        Author a = new Author();
        a.setName(authorForm.getName());
        Author author = authorRepository.save(a);
        return ResponseEntity.created(URI.create(String.format("/authors/%d", author.getId()))).build();
    }

    @GetMapping(path = "/{authorId}")
    public @ResponseBody ResponseEntity getAuthor(@RequestPart Integer authorId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);

        if (optionalAuthor.isPresent()) return ResponseEntity.ok(optionalAuthor.get());
        else return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{authorId}")
    public @ResponseBody ResponseEntity updateAuthor(@RequestPart Integer authorId,
                                                     @RequestBody AuthorForm authorForm) {
        Author author = null;
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);

        if (optionalAuthor.isPresent()) author = optionalAuthor.get();
        else return ResponseEntity.notFound().build();

        String name = authorForm.getName();
        if (name != null) author.setName(name);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
