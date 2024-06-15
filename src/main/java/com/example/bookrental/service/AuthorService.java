package com.example.bookrental.service;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.entity.Book;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springdoc.core.configuration.SpringDocUIConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    public String addAuthor (AuthorDto authorDto);
    public String updateAuthor (AuthorDto authorDto);
    public List<AuthorDto> getAllAuthor ();
    public List<AuthorDto> getDeletedAuthor ();
    public AuthorDto findById(Long id);
    public String deleteAuthor (Long id);
    public String getExcel(HttpServletResponse response) throws IOException, IllegalAccessException;
    public String excelToDb(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException;

    public Boolean sendHtmlMail() throws Exception;
}
