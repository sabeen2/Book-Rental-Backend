package com.example.bookrental.service;

import com.example.bookrental.dto.BookDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    public Book addBook(BookDto  bookDto,MultipartFile file) throws Exception;
    public Book updateBook(BookDto  bookDto);
    public List<Book> getAllBook();
    public Book findById(Long id);
    public String deleteBook(Long id);


}
