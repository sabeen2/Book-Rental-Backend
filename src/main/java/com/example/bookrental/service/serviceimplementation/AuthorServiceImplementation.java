package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.exception.CustomMessageSource;
import com.example.bookrental.exception.ExceptionMessages;
import com.example.bookrental.exception.NotFoundException;
import com.example.bookrental.mapper.AuthorMapper;
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
import java.util.Optional;

import static com.example.bookrental.utils.NullValues.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class AuthorServiceImplementation implements AuthorService {

    private final AuthorRepo authorRepo;
    private final ObjectMapper objectMapper;
    private final CustomMessageSource messageSource;
    private final AuthorMapper authorMapper;

    @Override
    public String addAuthor(AuthorDto authorDto) {
        Optional<Author> byName = authorRepo.findByName(authorDto.getName());
        if(byName.isPresent()){
            Author existingAuthor=byName.get();
            if (existingAuthor.isDeleted()) {
                existingAuthor.setDeleted(false);
                authorRepo.save(existingAuthor);
                return "member already existed so, active status is changed";
            }else {
                throw new NotFoundException(messageSource.get(ExceptionMessages.CONSTRAINT_VIOLATION.getCode()));
            }
        } else {
        Author author = objectMapper.convertValue(authorDto, Author.class);
        authorRepo.save(author);
        return messageSource.get(ExceptionMessages.SAVE.getCode()) +" id-:"+ author.getAuthorId();
    }
}

    String facebook = "df0f08c9-7d0e-4f6a-b0c7-5127a9eb1075";
    String Email = "df0f08c9-7d0e-4f6a-b0c7-5127a9eb1078";
    String baseUrl="http://localhost:8080";

    public static String generateUniqueUrl(String baseUrl, String endpoint) {
        String uuid="df0f08c9-7d0e-4f6a-b0c7-5127a9eb1075";
        return baseUrl + endpoint + "?" + uuid;
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
    public List<AuthorDto> getAllAuthor() {
        return authorMapper.getAllAuthors();
    }

    @Override
    public List<AuthorDto> getDeletedAuthor() {
        return authorMapper.getDeleted();
    }
    @Override
    public AuthorDto findById(Long id) {
        return authorMapper.getById(id)
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
