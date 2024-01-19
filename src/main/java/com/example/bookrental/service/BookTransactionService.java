package com.example.bookrental.service;
import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.entity.BookTransaction;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BookTransactionService {
    public BookTransaction addTransaction  (BookTransactionDto bookTransactionDto, HttpServletRequest request);
    public BookTransaction updateTransaction(BookTransactionDto bookTransactionDto,HttpServletRequest request);
    public List<BookTransaction> getAllTransaction();
    public BookTransaction findById(Long id);
    public String deleteTransaction (Long id);

}
