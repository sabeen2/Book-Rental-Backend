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
        return messageSource.get(ExceptionMessages.SAVE.getCode()) +" id-:"+ author.getAuthorId();
    }

    @Override
    public String updateAuthor(AuthorDto authorDto) {
        Author author = authorRepo.findById(authorDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        BeanUtils.copyProperties(authorDto, author, getNullPropertyNames(authorDto));
        authorRepo.save(author);
        return messageSource.get(ExceptionMessages.UPDATE.getCode())  +" id-:"+ author.getAuthorId();
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepo.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
    }

    @Override
    public AuthorDto findByAuthorId(Long id) {
        return authorRepo.findAuthorById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
    }

    @Override
    public String deleteAuthor(Long id) {
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.get(ExceptionMessages.NOT_FOUND.getCode())));
        authorRepo.delete(author);
        return author.getAuthorId() + messageSource.get(ExceptionMessages.DELETED.getCode()) ;
    }

    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException {
        ExcelGenerator.generateExcel(response, authorRepo.findAll(), "authorSheet", Author.class);
        return messageSource.get(ExceptionMessages.DOWNLOADED.getCode());
    }

    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        List<Author> authors = ExcelToDb.createExcel(file, Author.class);
        authorRepo.saveAll(authors);
        return messageSource.get(ExceptionMessages.EXPORT_EXCEL_SUCCESS.getCode());
    }
}
