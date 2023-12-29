package com.example.bookrental.Service;

import com.example.bookrental.Dto.BookDto;
import com.example.bookrental.Entity.Book;

import java.util.List;

public interface BookService {
    public Book addBook(BookDto  bookDto);
    public Book UpdateBook(BookDto  bookDto);
    public List<Book> getAllBook();
    public String deleteBook(long id);


}
