package com.example.bookrental.service;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;

import java.util.List;

public interface AuthorService {
    public Author addAuthor (AuthorDto authorDto);
    public Author updateAuthor (AuthorDto authorDto);
    public List<Author> getAllAuthor ();
    public Author findById(Long id);
    public String deleteAuthor (Long id);
}
