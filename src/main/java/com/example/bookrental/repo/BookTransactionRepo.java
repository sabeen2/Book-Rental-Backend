package com.example.bookrental.repo;

import com.example.bookrental.entity.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTransactionRepo extends JpaRepository<BookTransaction,Long> {

}
