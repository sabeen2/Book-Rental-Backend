package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.repo.AuthorRepo;
import com.example.bookrental.service.AuthorService;
import com.example.bookrental.utils.ExcelGenerator;
import com.example.bookrental.utils.ExcelToDb;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class AuthorServiceImplementation implements AuthorService {

    private final AuthorRepo authorRepo;
    private final ObjectMapper objectMapper;
    private final CustomMessageSource messageSource;

    @Override
    public String addAuthor(AuthorDto authorDto) {
        Author author;
        author = objectMapper.convertValue(authorDto, Author.class);
        authorRepo.save(author);
        return "author saved -:" + authorDto.getName();
    }

    @Override
    public String updateAuthor(AuthorDto authorDto) {
        Author author = authorRepo.findById(authorDto.getAuthorId()).orElseThrow(() -> new NotFoundException("Author Not Found"));
        BeanUtils.copyProperties(authorDto, author, getNullPropertyNames(authorDto));
        authorRepo.save(author);
        return "author Updated --";
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepo.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.FAIL.getCode())));
    }

    @Override
    public AuthorDto findByAuthorId(Long id) {
        return authorRepo.findAuthorById(id)
                .orElseThrow(() -> new NotFoundException("Author didnt exist"));
    }

    @Override
    public String deleteAuthor(Long id) {
        Author author = authorRepo.findById(id).orElseThrow(() -> new NotFoundException("Author Not Found"));
        authorRepo.delete(author);
        return author + "has been Deleted";
    }

    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        ExcelGenerator.generateExcel(response, authorRepo.findAll(), "authorSheet", Author.class);
        return "downloaded";
    }

    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        List<Author> authors = ExcelToDb.createExcel(file, Author.class);
        authorRepo.saveAll(authors);
        return "excel sheet data added";
    }
}
