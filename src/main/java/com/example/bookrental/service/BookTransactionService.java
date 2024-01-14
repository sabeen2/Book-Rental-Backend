package com.example.bookrental.service;
import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.entity.BookTransaction;

import java.util.List;

public interface BookTransactionService {
    public BookTransaction addTransaction  (BookTransactionDto bookTransactionDto);
    public BookTransaction updateTransaction(BookTransactionDto bookTransactionDto);
    public List<BookTransaction> getAllTransaction();
    public BookTransaction findById(Long id);
    public String deleteTransaction (Long id);

}
