package com.example.bookrental.repo;

import com.example.bookrental.dto.AuthorDto;
import com.example.bookrental.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepo extends JpaRepository<Author , Long > {
    @Query(value = "select new com.example.bookrental.dto.AuthorDto(a.authorId,a.name,a.email,a.mobileNumber) from Author a where a.authorId=?1")
    Optional<AuthorDto> findAuthorById(Long id);
}