package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.BookDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Book;
import com.example.bookrental.entity.Category;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.AuthorRepo;
import com.example.bookrental.repo.BookRepo;
import com.example.bookrental.repo.CategoryRepo;
import com.example.bookrental.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {
    private final ObjectMapper objectMapper;
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    private final AuthorRepo authorRepo;

    @Override
    public Book addBook(BookDto bookDto) {
        Long categoryId = bookDto.getCategoryId();
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        List<Long> authorId = bookDto.getAuthorId();
        List<Author> authors = authorRepo.findAllById(authorId);

        if (authors.size() != authorId.size()) {
            throw new NotFoundException("Authors do not exist");
        }
        Book book = objectMapper.convertValue(bookDto, Book.class);
        book.setCategory(category);
        book.setAuthors(authors);

        return bookRepo.save(book);
    }

    @Override
    public Book updateBook(BookDto bookDto) {
        Book book = bookRepo.findById(bookDto.getId()).orElseThrow(() -> new NotFoundException("Book Not Found"));
        BeanUtils.copyProperties(bookDto, book, getNullPropertyNames(bookDto));
        if (bookDto.getAuthorId() != null && !bookDto.getAuthorId().isEmpty()) {
            List<Author> updatedAuthorList = authorRepo.findAllById(bookDto.getAuthorId());
            book.setAuthors(updatedAuthorList);
        }
        if (bookDto.getCategoryId() != null) {
            Category updatedCategoryOptional = categoryRepo.findById(bookDto.getCategoryId()).orElseThrow(() -> new NotFoundException("Catagory dosent exist"));
            book.setCategory(updatedCategoryOptional);
        }
        return bookRepo.save(book);
//        Optional<Catagory> updatedCatagoryOptional = catagoryRepo.findById(bookDto.getCatagory_Id());
//        if(updatedCatagoryOptional.isPresent()){
//            Catagory UpdatedCatagory=updatedCatagoryOptional.get();
//            book.setCatagory(UpdatedCatagory);
//        }
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepo.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepo.findById(id).orElseThrow(()->new NotFoundException("Book does not exist"));
    }

    @Override
    public String deleteBook(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new NotFoundException("book not found"));
        bookRepo.delete(book);
        return book.toString() + " has been deleted";
    }
}
