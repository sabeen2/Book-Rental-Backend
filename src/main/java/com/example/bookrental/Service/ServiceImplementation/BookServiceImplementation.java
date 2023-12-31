package com.example.bookrental.Service.ServiceImplementation;

import com.example.bookrental.Dto.BookDto;
import com.example.bookrental.Dto.CatagoryDto;
import com.example.bookrental.Entity.Author;
import com.example.bookrental.Entity.Book;
import com.example.bookrental.Entity.Catagory;
import com.example.bookrental.Repo.AuthorRepo;
import com.example.bookrental.Repo.BookRepo;
import com.example.bookrental.Repo.CatagoryRepo;
import com.example.bookrental.Service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.example.bookrental.Utils.NullValues.getNullPropertyNames;

@Service
@AllArgsConstructor
public class BookServiceImplementation implements BookService {
    private final ObjectMapper objectMapper;
    private final BookRepo bookRepo;
    private final CatagoryRepo catagoryRepo;
    private final AuthorRepo authorRepo;
@Override
public Book addBook(BookDto bookDto) {
    long categoryId = bookDto.getCatagory_Id();
    Catagory category = catagoryRepo.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));

    List<Long> authorId = bookDto.getAuthorId();
    List<Author> authors = authorRepo.findAllById(authorId);

    if (authors.size() != authorId.size()) {
        throw new RuntimeException("Authors do not exist");
    }
    Book book = objectMapper.convertValue(bookDto, Book.class);
    book.setCatagory(category);
    book.setAuthors(authors);

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
