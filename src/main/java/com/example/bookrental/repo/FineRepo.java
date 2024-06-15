package com.example.bookrental.repo;

import com.example.bookrental.entity.BookTransaction;
import com.example.bookrental.entity.FineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepo extends JpaRepository<FineEntity ,Long> {

    FineEntity findByBookTransaction(BookTransaction bookTransaction);
}
