package com.example.bookrental.Service;

import com.example.bookrental.Dto.AuthorDto;
import com.example.bookrental.Dto.MemberDto;
import com.example.bookrental.Entity.Author;
import com.example.bookrental.Entity.Member;

import java.util.List;

public interface AuthorService {
    public Author addAuthor (AuthorDto authorDto);
    public Author updateAuthor (AuthorDto authorDto);
    public List<Author> getAllAuthor ();
    public String deleteAuthor (long id);
}
