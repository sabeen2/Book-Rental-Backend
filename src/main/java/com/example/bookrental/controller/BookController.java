package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.dto.BookDto;
import com.example.bookrental.entity.Book;
import com.example.bookrental.generic_response.GenericResponse;
import com.example.bookrental.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Lib/books")
@SecurityRequirement(name = "bookRental")
@Tag(name = "Book Controller", description = "APIs for managing Books")
public class BookController extends BaseController {
    private final BookService bookService;

    @Operation(summary = "Add Book", description = "Add Book to the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book added"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PostMapping("/add-Book")
    public GenericResponse<Book> addBook(@RequestBody @Valid BookDto bookDto) {
        return successResponse(bookService.addBook(bookDto), "New book added");
    }

    @Operation(summary = "Update Book", description = "update the available Book detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @PutMapping("/update-Book")
    public GenericResponse<Book> updateBook(@RequestBody BookDto bookDto) {
        return successResponse(bookService.updateBook(bookDto), "Book Updated");
    }

    @Operation(summary = "Get all Books", description = "Fetch all available Books detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available Books"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Books not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @GetMapping("/get-All-Books")
    public GenericResponse<List<Book>> getAllBook() {
        return successResponse(bookService.getAllBook(), "All available Books");
    }
    @Operation(summary = "Get book by id", description = "Fetch available Book detail based on  provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping("/get-By-Id")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public GenericResponse<Book> getById(@RequestParam Long id){
        return successResponse(bookService.findById(id),"book id-:"+id+"details");
    }
    @Operation(summary = "delete book by id", description = "delete available book detail based on  provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "book found and Deleted"),
            @ApiResponse(responseCode = "402", description = "book not found"),
            @ApiResponse(responseCode = "403" ,description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    @DeleteMapping("/delete-Book")
    public GenericResponse<String> deleteBook(@RequestParam long id) {
        return successResponse(bookService.deleteBook(id), "Book id-:" + id + " is deleted ");
    }
}
