package com.example.booklibrary.controller;

import com.example.booklibrary.entity.Author;
import com.example.booklibrary.form.AuthorForm;
import com.example.booklibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        authorRepository.save(a);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
