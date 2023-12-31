package com.example.bookrental.Repo;

import com.example.bookrental.Entity.Book;
import com.example.bookrental.Entity.BookTransaction;
import com.example.bookrental.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookTransactionRepo extends JpaRepository<BookTransaction,Long> {

}
