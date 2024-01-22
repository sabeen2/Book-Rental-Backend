package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Book;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.AuthorRepo;
import com.example.bookrental.service.AuthorService;
import com.example.bookrental.utils.ExcelGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class AuthorServiceImplementation implements AuthorService {

    private final AuthorRepo authorRepo;
    private final ObjectMapper objectMapper;

    @Override
    public Author addAuthor(AuthorDto authorDto) {
        Author author;
        author = objectMapper.convertValue(authorDto, Author.class);
        return authorRepo.save(author);
    }

    @Override
    public Author updateAuthor(AuthorDto authorDto) {
        Author author = authorRepo.findById(authorDto.getAuthorId()).orElseThrow(() -> new NotFoundException("Author Not Found"));
        BeanUtils.copyProperties(authorDto, author, getNullPropertyNames(authorDto));
        return authorRepo.save(author);
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepo.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Author didnt exist"));
    }

    @Override
    public String deleteAuthor(Long id) {
        Author author = authorRepo.findById(id).orElseThrow(() -> new NotFoundException("Author Not Found"));
        authorRepo.delete(author);
        return author + "has been Deleted";
    }

    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        ExcelGenerator.generateExcel(response,authorRepo.findAll(),"author sheet",Author.class);
        return "downloaded";
    }
}
