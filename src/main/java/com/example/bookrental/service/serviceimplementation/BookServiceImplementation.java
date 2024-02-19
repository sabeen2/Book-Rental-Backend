package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.BookDto;
import com.example.bookrental.dto.responsedto.BookResponse;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Book;
import com.example.bookrental.entity.Category;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.mapper.BookMapper;
import com.example.bookrental.repo.AuthorRepo;
import com.example.bookrental.repo.BookRepo;
import com.example.bookrental.repo.CategoryRepo;
import com.example.bookrental.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {
    private final ObjectMapper objectMapper;
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    private final AuthorRepo authorRepo;
    private final CustomMessageSource messageSource;
    private final BookMapper bookMapper;

    @Override
    public String addBook(BookDto bookDto, MultipartFile file) throws Exception {
        Category category = categoryRepo.findById(bookDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));

        List<Long> authorId = bookDto.getAuthorId();
        List<Author> authors = authorRepo.findAllById(authorId);

        if (authors.size() != authorId.size()) {
            throw new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode()));
        }
//        String path = saveImage("C:\\Users\\shyam prasad\\Pictures\\Saved Pictures\\", file);
        String path = saveImage("/uploads/", file);
        bookDto.setPhoto(path);
        Book book = objectMapper.convertValue(bookDto, Book.class);
        book.setCategory(category);
        book.setAuthors(authors);
        bookRepo.save(book);
        return "Book added-" + bookDto.getName() + "\n id- " + book.getId();
    }

    public static String saveImage(String path, MultipartFile file) throws IOException {
        if (file == null) {
            throw new NotFoundException("photo is req");
        }
        String name = UUID.randomUUID() + "-" + file.getOriginalFilename();
        String filePath = path + File.separator + name;
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return name;
    }

    @Override
    public String updateBook(BookDto bookDto) {
        Book book = bookRepo.findById(bookDto.getId())
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        BeanUtils.copyProperties(bookDto, book, getNullPropertyNames(bookDto));
        if (bookDto.getAuthorId() != null && !bookDto.getAuthorId().isEmpty()) {
            List<Author> updatedAuthorList = authorRepo.findAllById(bookDto.getAuthorId());
            book.setAuthors(updatedAuthorList);
        }
        if (bookDto.getCategoryId() != null) {
            Category updatedCategoryOptional = categoryRepo.findById(bookDto.getCategoryId()).orElseThrow(() -> new NotFoundException("Catagory dosent exist"));
            book.setCategory(updatedCategoryOptional);
        }
        bookRepo.save(book);
        return "Book Updated" + bookDto.getName();
    }

    @Override
    public List<BookResponse> getAllBook() {
        return bookMapper.getAllBooks();
    }


    @Override
    public BookResponse findById(Long id) {
        return bookMapper.getBookByID(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
    }

    @Override
    public String deleteBook(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        bookRepo.delete(book);
        return book.getName() + " has been deleted";
    }

    public void getImage(Long id, HttpServletResponse response) throws IOException {
        Book book = bookRepo.findById(id).orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        String name = book.getPhoto();
//        InputStream stream = new FileInputStream("C:\\Users\\shyam prasad\\Pictures\\Saved Pictures\\" + name);
        InputStream stream = new FileInputStream("/uploads/" + name);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/jpeg");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=book_" + id + ".jpg";
        response.setHeader(headerKey, headerValue);
        byte[] imageByte = IOUtils.toByteArray(stream);
        out.write(imageByte);
        out.flush();
        out.close();
    }
}
