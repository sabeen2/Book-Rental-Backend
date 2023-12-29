package com.example.bookrental.Controller;

import com.example.bookrental.Dto.BookDto;
import com.example.bookrental.Entity.Book;
import com.example.bookrental.Service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    @PostMapping("/addBook")
    public Book addBook(@RequestBody BookDto bookDto){
        return bookService.addBook(bookDto);
    }
    @PutMapping("/updateBook")
    public Book UpdateBook(@RequestBody BookDto  bookDto){
        return bookService.UpdateBook(bookDto);
    }
    @GetMapping("/getAllBooks")
    public List<Book> getAllBook(){
        return bookService.getAllBook();
    }
    @DeleteMapping("/deleteBook")
    public String deleteBook(@RequestParam long id){
        return bookService.deleteBook(id);
    }


}
