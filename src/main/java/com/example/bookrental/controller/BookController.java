package com.example.bookrental.controller;

import com.example.bookrental.dto.BookDto;
import com.example.bookrental.entity.Book;
import com.example.bookrental.service.BookService;
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
    public Book addBook(@RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto);
    }

    @PutMapping("/update-Book")
    public Book updateBook(@RequestBody BookDto bookDto) {
        return bookService.updateBook(bookDto);
    }

    @GetMapping("/get-All-Books")
    public List<Book> getAllBook() {
        return bookService.getAllBook();
    }

    @DeleteMapping("/delete-Book")
    public String deleteBook(@RequestParam long id) {
        return bookService.deleteBook(id);
    }


}
