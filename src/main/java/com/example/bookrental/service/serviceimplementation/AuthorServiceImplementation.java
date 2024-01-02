package com.example.bookrental.service.serviceimplementation;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import com.example.bookrental.repo.AuthorRepo;
import com.example.bookrental.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bookrental.Utils.NullValues.getNullPropertyNames;

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
        Author author = authorRepo.findById(authorDto.getAuthorId()).orElseThrow(() -> new RuntimeException("Author Not Found"));
        BeanUtils.copyProperties(authorDto, author, getNullPropertyNames(authorDto));
        return authorRepo.save(author);
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepo.findAll();
    }

    @Override
    public String deleteAuthor(Long id) {
        Author author = authorRepo.findById(id).orElseThrow(() -> new RuntimeException("Author Not Found"));
        authorRepo.delete(author);
        return author.toString() + "has been Deleted";
    }
}
