package com.example.bookrental.service;
import com.example.bookrental.dto.BookTransactionDto;
import com.example.bookrental.dto.responsedto.BookTransactionResponse;
import com.example.bookrental.entity.BookTransaction;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BookTransactionService {
    public String addTransaction  (BookTransactionDto bookTransactionDto, HttpServletRequest request);
    public String updateTransaction(BookTransactionDto bookTransactionDto);
    public List<BookTransactionResponse> getAllTransaction();
    public List<BookTransactionResponse> getNames();
    public BookTransactionResponse findById(Long id);
    public String deleteTransaction (Long id);
}
