package com.example.bookrental.controller;

import com.example.bookrental.dto.BookDto;
import com.example.bookrental.entity.Book;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Lib/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PostMapping("/add-Book")
    public GenericResponse<Book> addBook(@RequestBody @Valid BookDto bookDto) {
        return GenericResponse.<Book>builder()
                .success(true)
                .message("New Book added")
                .data(bookService.addBook(bookDto))
                .build();

    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PutMapping("/update-Book")
    public GenericResponse<Book> updateBook(@RequestBody BookDto bookDto) {
        return GenericResponse.<Book>builder()
                .success(true)
                .message("Book updated")
                .data(bookService.updateBook(bookDto))
                .build();
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @GetMapping("/get-All-Books")
    public GenericResponse<List<Book>> getAllBook() {
        return GenericResponse.<List<Book>>builder()
                .success(true)
                .message("All available books")
                .data(bookService.getAllBook())
                .build();
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @DeleteMapping("/delete-Book")
    public GenericResponse<String> deleteBook(@RequestParam long id) {
        return GenericResponse.<String>builder()
                .success(true)
                .message("Book id-:"+id+" is deleted ")
                .data(bookService.deleteBook(id))
                .build();
    }
}
