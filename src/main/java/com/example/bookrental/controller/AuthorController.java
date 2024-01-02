package com.example.bookrental.controller;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/add-Author")
    public Author addAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.addAuthor(authorDto);
    }

    @PutMapping("/update-Author")
    public Author updateAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.updateAuthor(authorDto);
    }

    @GetMapping("/all-Authors")
    public List<Author> getAllAuthor() {
        return authorService.getAllAuthor();
    }

    @DeleteMapping("/delete")
    public String deleteAuthor(@RequestParam long id) {
        return authorService.deleteAuthor(id);
    }
}
