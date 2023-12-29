package com.example.bookrental.Service.ServiceImplementation;

import com.example.bookrental.Dto.AuthorDto;
import com.example.bookrental.Dto.MemberDto;
import com.example.bookrental.Entity.Author;
import com.example.bookrental.Entity.Member;
import com.example.bookrental.Repo.AuthorRepo;
import com.example.bookrental.Service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bookrental.Utils.NullValues.getNullPropertyNames;

@Service
@AllArgsConstructor
public class AuthorServiceImplementation implements AuthorService {

    private final AuthorRepo authorRepo;
    private final ObjectMapper objectMapper;

    @Override
    public Author addAuthor(AuthorDto authorDto) {
        Author author;
        author=objectMapper.convertValue(authorDto,Author.class);
        return authorRepo.save(author);
    }

    @Override
    public Author updateAuthor(AuthorDto authorDto) {
        Author author= authorRepo.findById(authorDto.getAuthor_id()).orElseThrow(()->new RuntimeException("Author Not Found"));
        BeanUtils.copyProperties(authorDto,author,getNullPropertyNames(authorDto));
        return authorRepo.save(author);
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepo.findAll();
    }

    @Override
    public String deleteAuthor(long id) {
    Author author=authorRepo.findById(id).orElseThrow(()->new RuntimeException("Author Not Found"));
    authorRepo.delete(author);
    return author.toString()+"has been Deleted";
    }
}
