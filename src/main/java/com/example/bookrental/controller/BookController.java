package com.example.bookrental.controller;

import com.example.bookrental.dto.BookDto;
import com.example.bookrental.entity.Book;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping("/add-Book")
    public GenericResponse<Book> addBook(@RequestBody @Valid BookDto bookDto) {
        return GenericResponse.<Book>builder()
                .success(true)
                .message("New Book added")
                .data(bookService.addBook(bookDto))
                .build();

    }

    @PutMapping("/update-Book")
    public GenericResponse<Book> updateBook(@RequestBody BookDto bookDto) {
        return GenericResponse.<Book>builder()
                .success(true)
                .message("Book updated")
                .data(bookService.updateBook(bookDto))
                .build();
    }

    @GetMapping("/get-All-Books")
    public GenericResponse<List<Book>> getAllBook() {
        return GenericResponse.<List<Book>>builder()
                .success(true)
                .message("All available books")
                .data(bookService.getAllBook())
                .build();
    }

    @DeleteMapping("/delete-Book")
    public GenericResponse<String> deleteBook(@RequestParam long id) {
        return GenericResponse.<String>builder()
                .success(true)
                .message("Book id-:"+id+" is deleted ")
                .data(bookService.deleteBook(id))
                .build();
    }


}
