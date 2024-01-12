package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
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
public class BookController extends BaseController {
    private final BookService bookService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PostMapping("/add-Book")
    public GenericResponse<Book> addBook(@RequestBody @Valid BookDto bookDto) {
        return successResponse(bookService.addBook(bookDto), "New book added");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PutMapping("/update-Book")
    public GenericResponse<Book> updateBook(@RequestBody BookDto bookDto) {
        return successResponse(bookService.updateBook(bookDto), "Book Updated");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @GetMapping("/get-All-Books")
    public GenericResponse<List<Book>> getAllBook() {
        return successResponse(bookService.getAllBook(), "All available Books");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @DeleteMapping("/delete-Book")
    public GenericResponse<String> deleteBook(@RequestParam long id) {
        return successResponse(bookService.deleteBook(id), "Book id-:" + id + " is deleted ");
    }
}
