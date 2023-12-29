package com.example.bookrental.Service.ServiceImplementation;

import com.example.bookrental.Dto.BookDto;
import com.example.bookrental.Entity.Book;
import com.example.bookrental.Repo.BookRepo;
import com.example.bookrental.Service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bookrental.Utils.NullValues.getNullPropertyNames;

@Service
@AllArgsConstructor
public class BookServiceImplementation implements BookService {
    private final ObjectMapper objectMapper;
    private final BookRepo bookRepo;


    @Override
    public Book addBook(BookDto bookDto) {
    Book book=objectMapper.convertValue(bookDto,Book.class);
    return bookRepo.save(book);
    }

    @Override
    public Book UpdateBook(BookDto bookDto) {
        Book book=bookRepo.findById(bookDto.getId()).orElseThrow(()->new RuntimeException("Book Not Found"));
        BeanUtils.copyProperties(bookDto, book, getNullPropertyNames(bookDto));
        return bookRepo.save(book);

    }

    @Override
    public List<Book> getAllBook() {
        return bookRepo.findAll();
    }

    @Override
    public String deleteBook(long id) {
        Book book=bookRepo.findById(id).orElseThrow(()->new RuntimeException("book not found"));
        bookRepo.delete(book);
        return book.toString() +" has been deleted";
    }
}
