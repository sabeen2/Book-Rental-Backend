package com.example.bookrental.controller;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.AuthorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Lib/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/add-Author")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Author> addAuthor(@RequestBody  @Valid AuthorDto authorDto) {
        return GenericResponse.<Author>builder()
                .success(true)
                .message("Author added")
                .data( authorService.addAuthor(authorDto))
                .build();
    }

    @PutMapping("/update-Author")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Author> updateAuthor(@RequestBody AuthorDto authorDto) {
        return GenericResponse.<Author>builder()
                .success(true)
                .message("Author updated")
                .data(authorService.updateAuthor(authorDto))
                .build();
    }

    @GetMapping("/all-Authors")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<List<Author>> getAllAuthor() {

        return GenericResponse.<List<Author>>builder()
                .success(true)
                .message("All Available authors")
                .data(authorService.getAllAuthor())
                .build();
    }
    @GetMapping("/find-by-id")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Author> findAuthorById(@RequestParam Long id){
        return GenericResponse.<Author>builder()
                .success(true)
                .message("User id-:"+id)
                .data(authorService.findById(id))
                .build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<String> deleteAuthor(@RequestParam Long id) {
        return GenericResponse.<String>builder()
                .success(true)
                .message("User id:-"+id+" deleted")
                .data(authorService.deleteAuthor(id))
                .build();
    }
}
