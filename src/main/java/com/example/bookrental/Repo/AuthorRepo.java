package com.example.bookrental.Repo;

import com.example.bookrental.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author , Long > {

}
