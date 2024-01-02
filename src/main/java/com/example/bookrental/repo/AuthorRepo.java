package com.example.bookrental.repo;

import com.example.bookrental.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author , Long > {

}
