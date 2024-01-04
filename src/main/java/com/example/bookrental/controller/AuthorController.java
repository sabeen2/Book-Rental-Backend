package com.example.bookrental.controller;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.AuthorService;
import jakarta.validation.Valid;
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
    public GenericResponse<Author> addAuthor(@RequestBody  @Valid AuthorDto authorDto) {
        return GenericResponse.<Author>builder()
                .success(true)
                .message("Author added")
                .data( authorService.addAuthor(authorDto))
                .build();
    }

    @PutMapping("/update-Author")
    public GenericResponse<Author> updateAuthor(@RequestBody AuthorDto authorDto) {
        return GenericResponse.<Author>builder()
                .success(true)
                .message("Author updated")
                .data(authorService.updateAuthor(authorDto))
                .build();
    }

    @GetMapping("/all-Authors")
    public GenericResponse<List<Author>> getAllAuthor() {

        return GenericResponse.<List<Author>>builder()
                .success(true)
                .message("All Available authors")
                .data(authorService.getAllAuthor())
                .build();
    }

    @DeleteMapping("/delete")
    public GenericResponse<String> deleteAuthor(@RequestParam long id) {
        return GenericResponse.<String>builder()
                .success(true)
                .message("User id:-"+id+" deleted")
                .data(authorService.deleteAuthor(id))
                .build();
    }
}
